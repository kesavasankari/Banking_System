package com.example.AuthService.controller;


import com.example.AuthService.Dto.ApiResponse;
import com.example.AuthService.Dto.JwtResponse;
import com.example.AuthService.Dto.LoginRequest;
import com.example.AuthService.Dto.RegisterRequest;
import com.example.AuthService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Register User
    @PostMapping("/register")
    public ApiResponse register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    // Login User
    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }
}