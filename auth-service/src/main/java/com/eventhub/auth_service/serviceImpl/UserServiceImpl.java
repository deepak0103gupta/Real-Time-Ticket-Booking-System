package com.eventhub.auth_service.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.eventhub.auth_service.dto.LoginRequest;
import com.eventhub.auth_service.dto.LoginResponse;
import com.eventhub.auth_service.dto.SignupRequest;
import com.eventhub.auth_service.dto.UserResponse;
import com.eventhub.auth_service.entity.User;
import com.eventhub.auth_service.entity.UserRoleEnum;
import com.eventhub.auth_service.exception.InvalidCredentialsException;
import com.eventhub.auth_service.exception.UserAlreadyExistException;
import com.eventhub.auth_service.mapper.UserMapper;
import com.eventhub.auth_service.repository.UserRepository;
import com.eventhub.auth_service.service.UserService;
import com.eventhub.auth_service.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder,JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserResponse registerUser(SignupRequest signupRequest) {

        if(userRepository.findByEmail(signupRequest.getEmail()) != null){
            throw new UserAlreadyExistException("Email already exists");
        }

        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(UserRoleEnum.USER);
        user.setCreatedAt(LocalDateTime.now());


       

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        try{
            User user = userRepository.findByEmail(loginRequest.getEmail());
            UserResponse userResponse = userMapper.toUserResponse(user);
            if(user.getEmail() == null){
                throw new InvalidCredentialsException("User not found please register");
            }
            if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
                throw new InvalidCredentialsException("Incorrect password");
            }
            String token = jwtUtil.generateToken(user);
            return userMapper.toLoginResponse(userResponse, token);
            
            
            
        } catch (InvalidCredentialsException e) {
            throw e;
        }
    }

    
}
