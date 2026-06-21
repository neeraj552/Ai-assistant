package com.neeraj.assistant.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.neeraj.assistant.auth.dto.AuthResponse;
import com.neeraj.assistant.auth.dto.LoginRequest;
import com.neeraj.assistant.auth.dto.RegisterRequest;
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

    public AuthResponse register(RegisterRequest request){

        if(userRepository.existsByEmail(request.getEmail())){
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

    public AuthResponse login(LoginRequest request){
        
        User user = userRepository.findByemail(request.getEmail())
                   .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new RuntimeException("Invalid email or password");
        }
        
        String token = jwtService.generateToken((user.getEmail()));
        return new AuthResponse(token);



    }

}
