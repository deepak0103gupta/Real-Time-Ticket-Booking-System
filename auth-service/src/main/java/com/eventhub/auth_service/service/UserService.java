package com.eventhub.auth_service.service;

import org.springframework.stereotype.Service;

import com.eventhub.auth_service.dto.LoginRequest;
import com.eventhub.auth_service.dto.LoginResponse;
import com.eventhub.auth_service.dto.SignupRequest;
import com.eventhub.auth_service.dto.UserResponse;

@Service
public interface UserService {
    UserResponse registerUser(SignupRequest signupRequest);
    LoginResponse loginUser(LoginRequest loginRequest);
}
