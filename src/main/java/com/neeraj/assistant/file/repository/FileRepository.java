package com.neeraj.assistant.file.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.neeraj.assistant.file.entity.FileDocument;
import com.neeraj.assistant.user.entity.User;
import com.neeraj.assistant.file.entity.FileSortType;

@Repository
public interface FileRepository extends JpaRepository<FileDocument, UUID> {

    List<FileDocument> findByUser(User user);

    Optional<FileDocument> findByIdAndUser(UUID id, User user);

    long countByUser(User user);

    @Query("""
        SELECT COALESCE(SUM(f.size), 0)
        FROM FileDocument f
        WHERE f.user = :user
    """)
    Long getTotalStorageUsed(User user);

    List<FileDocument> findByUserAndOriginalNameContainingIgnoreCase(
            User user,
            String originalName
    );

    List<FileDocument> findByUserOrderByUploadAtDesc(User user);

    List<FileDocument> findByUserOrderByUploadAtAsc(User user);

    List<FileDocument> findByUserOrderByOriginalNameAsc(User user);

    List<FileDocument> findByUserOrderByOriginalNameDesc(User user);

    List<FileDocument> findByUserOrderBySizeAsc(User user);

    List<FileDocument> findByUserOrderBySizeDesc(User user);

}