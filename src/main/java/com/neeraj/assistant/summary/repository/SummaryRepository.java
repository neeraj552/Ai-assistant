package com.neeraj.assistant.summary.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neeraj.assistant.file.entity.FileDocument;
import com.neeraj.assistant.summary.entity.Summary;
import com.neeraj.assistant.user.entity.User;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, UUID> {

    Optional<Summary> findByFile(FileDocument file);

    long countByFileUser(User user);


}
