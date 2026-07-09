package com.example.AuthService.service;

import com.example.AuthService.Dto.ApiResponse;
import com.example.AuthService.Dto.JwtResponse;
import com.example.AuthService.Dto.LoginRequest;
import com.example.AuthService.Dto.RegisterRequest;
import com.example.AuthService.entity.User;
import com.example.AuthService.exception.InvalidPasswordException;
import com.example.AuthService.exception.UserAlreadyExistsException;
import com.example.AuthService.exception.UserNotFoundException;
import com.example.AuthService.repository.UserRepository;
import com.example.AuthService.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public ApiResponse register(RegisterRequest request) {

        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        // Create User object
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("CUSTOMER") // Default role
                .build();

        // Save user
        userRepository.save(user);

        return new ApiResponse("User registered successfully");
    }

    @Override
    public JwtResponse login(LoginRequest request) {

        // Find user by username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid Password");
        }

        // Generate JWT Token with Role
        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole()
        );

        // Return JWT Response
        return new JwtResponse(
                token,
                "Bearer",
                user.getUsername(),
                user.getRole()
        );
    }
}