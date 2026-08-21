package com.eventhub.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.auth_service.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
