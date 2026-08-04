package com.neeraj.assistant.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neeraj.assistant.auth.dto.AuthResponse;
import com.neeraj.assistant.auth.dto.LoginRequest;
import com.neeraj.assistant.auth.dto.RegisterRequest;
import com.neeraj.assistant.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.neeraj.assistant.auth.dto.ForgotPasswordRequest;
import com.neeraj.assistant.auth.dto.ResetPasswordRequest;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(

        @Valid @RequestBody ForgotPasswordRequest request

    ) {

    authService.forgotPassword(request);

    return ResponseEntity.ok().build();

    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(

        @Valid @RequestBody ResetPasswordRequest request

    ) {

    authService.resetPassword(request);

    return ResponseEntity.ok().build();

    }
    
}
