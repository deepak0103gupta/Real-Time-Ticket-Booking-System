package com.eventhub.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.auth_service.dto.SignupRequest;
import com.eventhub.auth_service.dto.UserResponse;
import com.eventhub.auth_service.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<UserResponse> registerUser(@RequestBody SignupRequest signupRequest) {
        UserResponse userResponse = userService.registerUser(signupRequest);
        return ResponseEntity.ok(userResponse);
    }
}
