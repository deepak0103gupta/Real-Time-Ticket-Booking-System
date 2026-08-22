package com.eventhub.auth_service.dto;

import java.time.LocalDateTime;

import com.eventhub.auth_service.entity.UserRoleEnum;

import lombok.Data;

@Data
public class UserResponse {
    private Long userid;
    private String name;
    private String email;
    private UserRoleEnum role;
    private LocalDateTime createdAt;
}
