package com.example.AuthService.service;



import com.example.AuthService.Dto.ApiResponse;
import com.example.AuthService.Dto.JwtResponse;
import com.example.AuthService.Dto.LoginRequest;
import com.example.AuthService.Dto.RegisterRequest;

public interface UserService {

    ApiResponse register(RegisterRequest request);

    JwtResponse login(LoginRequest request);

}