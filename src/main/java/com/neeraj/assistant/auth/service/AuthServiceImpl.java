package com.neeraj.assistant.auth.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neeraj.assistant.auth.dto.AuthResponse;
import com.neeraj.assistant.auth.dto.ForgotPasswordRequest;
import com.neeraj.assistant.auth.dto.LoginRequest;
import com.neeraj.assistant.auth.dto.RegisterRequest;
import com.neeraj.assistant.auth.dto.ResetPasswordRequest;
import com.neeraj.assistant.auth.entity.PasswordResetToken;
import com.neeraj.assistant.auth.exceptions.InvalidCrendentialsException;
import com.neeraj.assistant.auth.exceptions.InvalidResetTokenException;
import com.neeraj.assistant.auth.exceptions.ResetTokenExpiredException;
import com.neeraj.assistant.auth.repository.PaasswordResetTokenRepository;
import com.neeraj.assistant.mail.service.EmailService;
import com.neeraj.assistant.user.entity.Role;
import com.neeraj.assistant.user.entity.User;
import com.neeraj.assistant.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PaasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByemail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCrendentialsException(
                                "Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new InvalidCrendentialsException(
                    "Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {

        User user = userRepository.findByemail(request.email())
                .orElse(null);

        // Don't reveal whether the email exists
        if (user == null) {
            return;
        }

        PasswordResetToken existingToken =
                passwordResetTokenRepository.findByUser(user)
                        .orElse(null);

        if (existingToken != null) {
            passwordResetTokenRepository.delete(existingToken);
            passwordResetTokenRepository.flush();
        }

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .createdAt(LocalDateTime.now())
                .expirDate(LocalDateTime.now().plusMinutes(15))
                .build();

        passwordResetTokenRepository.save(resetToken);

        String resetLink =
                frontendUrl + "/reset-password?token=" + token;

        emailService.sendPasswordResetEmail(
                user.getEmail(),
                resetLink
        );
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {

        PasswordResetToken resetToken =
                passwordResetTokenRepository.findByToken(request.token())
                        .orElseThrow(() ->
                                new InvalidResetTokenException(
                                        "Invalid reset token"));

        if (resetToken.getExpirDate().isBefore(LocalDateTime.now())) {

            passwordResetTokenRepository.delete(resetToken);

            throw new ResetTokenExpiredException(
                    "Reset token has expired");
        }

        User user = resetToken.getUser();

        user.setPassword(
                passwordEncoder.encode(request.password())
        );

        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }
}