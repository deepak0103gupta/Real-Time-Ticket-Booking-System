package com.eventhub.booking_service.serviceImpl;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.eventhub.booking_service.service.SeatHoldService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatHoldServiceImpl implements SeatHoldService{
    private final RedissonClient redissonClient;
    private final StringRedisTemplate redisTemplate;

    @Value("${booking.hold.duration-seconds}")
    private long holdDurationSeconds;

    private static final long LOCK_WAIT_SECONDS = 3;   // how long to wait to acquire the lock
    private static final long LOCK_LEASE_SECONDS = 5;  // safety auto-release if we crash mid-operation

    @Override
    public boolean tryHoldSeat(Long eventId, Long seatId, Long userId) {
        String lockKey = "lock:seat:" + eventId + ":" + seatId;
        String holdKey = "hold:seat:" + eventId + ":" + seatId;

        RLock lock = redissonClient.getLock(lockKey);
        boolean acquired = false;
        try {
            // Block up to LOCK_WAIT_SECONDS trying to get the lock;
            // auto-release after LOCK_LEASE_SECONDS even if we crash (safety net)
            acquired = lock.tryLock(LOCK_WAIT_SECONDS, LOCK_LEASE_SECONDS, TimeUnit.SECONDS);
            if (!acquired) {
                log.warn("Could not acquire lock for seat {} in event {}", seatId, eventId);
                return false;
            }

            // Critical section — only one thread across the whole cluster runs this
            // for this exact seat at a time.
            Boolean alreadyHeld = redisTemplate.hasKey(holdKey);
            if (Boolean.TRUE.equals(alreadyHeld)) {
                return false; // someone else already holds this seat
            }

            redisTemplate.opsForValue().set(
                    holdKey,
                    String.valueOf(userId),
                    holdDurationSeconds,
                    TimeUnit.SECONDS
            );
            return true;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            if (acquired && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    public void releaseHold(Long eventId, Long seatId) {
        String holdKey = "hold:seat:" + eventId + ":" + seatId;
        redisTemplate.delete(holdKey);
    }

    @Override
    public boolean isHeldByUser(Long eventId, Long seatId, Long userId) {
        String holdKey = "hold:seat:" + eventId + ":" + seatId;
        String heldBy = redisTemplate.opsForValue().get(holdKey);
        return heldBy != null && heldBy.equals(String.valueOf(userId));
    }
}
