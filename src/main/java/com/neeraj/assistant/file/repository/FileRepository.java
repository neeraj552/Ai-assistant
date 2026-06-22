package com.neeraj.assistant.file.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neeraj.assistant.file.entity.FileDocument;
import com.neeraj.assistant.user.entity.User;

@Repository
public interface FileRepository extends JpaRepository<FileDocument, UUID>{
    List<FileDocument> findByUser(User user);

}
