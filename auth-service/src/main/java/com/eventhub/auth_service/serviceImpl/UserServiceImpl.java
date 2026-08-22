package com.eventhub.auth_service.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.eventhub.auth_service.dto.LoginRequest;
import com.eventhub.auth_service.dto.SignupRequest;
import com.eventhub.auth_service.dto.UserResponse;
import com.eventhub.auth_service.entity.User;
import com.eventhub.auth_service.entity.UserRoleEnum;
import com.eventhub.auth_service.exception.InvalidCredentialsException;
import com.eventhub.auth_service.exception.UserAlreadyExistException;
import com.eventhub.auth_service.mapper.UserMapper;
import com.eventhub.auth_service.repository.UserRepository;
import com.eventhub.auth_service.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse registerUser(SignupRequest signupRequest) {

        if(userRepository.findByEmail(signupRequest.getEmail()) != null){
            throw new UserAlreadyExistException("Email already exists");
        }

        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        user.setRole(UserRoleEnum.USER);
        user.setCreatedAt(LocalDateTime.now());


       

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse loginUser(LoginRequest loginRequest) {
        try{
            User user = userRepository.findByEmail(loginRequest.getEmail());
            if(user != null && user.getPassword().equals(loginRequest.getPassword())){
                return userMapper.toUserResponse(user);
            }else{
                throw new InvalidCredentialsException("Invalid email or password");
            }
            
        } catch (InvalidCredentialsException e) {
            throw e;
        }
    }

    
}
