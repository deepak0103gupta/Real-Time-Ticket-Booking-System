package com.eventhub.auth_service.mapper;

import org.springframework.stereotype.Component;

import com.eventhub.auth_service.dto.LoginResponse;
import com.eventhub.auth_service.dto.UserResponse;
import com.eventhub.auth_service.entity.User;

@Component
public class UserMapper {
    public UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserid(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        userResponse.setCreatedAt(user.getCreatedAt());
        return userResponse;
    }

    public LoginResponse toLoginResponse(UserResponse userResponse, String token){
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserResponse(userResponse);
        loginResponse.setToken(token);
        return loginResponse;
    }
}
