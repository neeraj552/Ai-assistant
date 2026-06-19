package com.neeraj.assistant.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neeraj.assistant.user.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByemail(String email);
    boolean existsByEmail(String email);

}
