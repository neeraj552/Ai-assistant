package com.neeraj.assistant.auth.service;

import com.neeraj.assistant.auth.dto.AuthResponse;
import com.neeraj.assistant.auth.dto.LoginRequest;
import com.neeraj.assistant.auth.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest rquest);

    AuthResponse login(LoginRequest request);

}
