package com.neeraj.assistant.auth.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.neeraj.assistant.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "password_reset_token")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false, unique = true)
    private String token;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "user_id", nullable =  false)
    private User user;
    
    @Column(nullable = false)
    private LocalDateTime expirDate;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;

}
