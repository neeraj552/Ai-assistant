package com.neeraj.assistant.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neeraj.assistant.auth.entity.PasswordResetToken;
import com.neeraj.assistant.user.entity.User;

public interface PaasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {

    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUser(User user);

    void deleteByUser(User user);

}
