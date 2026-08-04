package com.neeraj.assistant.auth.service;

import com.neeraj.assistant.auth.dto.AuthResponse;
import com.neeraj.assistant.auth.dto.ForgotPasswordRequest;
import com.neeraj.assistant.auth.dto.LoginRequest;
import com.neeraj.assistant.auth.dto.RegisterRequest;
import com.neeraj.assistant.auth.dto.ResetPasswordRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest rquest);

    AuthResponse login(LoginRequest request);

    void  resetPassword(ResetPasswordRequest request);

    void  forgotPassword(ForgotPasswordRequest request);



}
