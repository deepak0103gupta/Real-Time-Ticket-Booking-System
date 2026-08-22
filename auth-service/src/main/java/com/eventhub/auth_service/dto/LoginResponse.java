package com.eventhub.auth_service.dto;

import lombok.Data;

@Data
public class LoginResponse {
    String token;
    UserResponse userResponse;
}
