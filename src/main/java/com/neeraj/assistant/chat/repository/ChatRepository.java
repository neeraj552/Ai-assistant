package com.neeraj.assistant.chat.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neeraj.assistant.chat.entity.ChatMessage;
import com.neeraj.assistant.file.entity.FileDocument;
import com.neeraj.assistant.user.entity.User;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, UUID>{

    List<ChatMessage> findByFileAndUserOrderByCreatedAtAsc(
        FileDocument file,
        User user
    );

    void deleteByFileAndUser(
        FileDocument file,
        User user
    );

    long countByUser(User user);

}
