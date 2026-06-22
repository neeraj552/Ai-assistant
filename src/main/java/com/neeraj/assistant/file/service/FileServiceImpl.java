package com.neeraj.assistant.file.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.neeraj.assistant.common.security.SecurityUtils;
import com.neeraj.assistant.file.dto.FileUploadResponse;
import com.neeraj.assistant.file.entity.FileDocument;
import com.neeraj.assistant.file.entity.UploadStatus;
import com.neeraj.assistant.file.repository.FileRepository;
import com.neeraj.assistant.file.util.FileStorageUtil;
import com.neeraj.assistant.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    private final FileStorageUtil fileStorageUtil;

    @Override
    public FileUploadResponse uploadFile(MultipartFile file) {

    if (file.isEmpty()) {
        throw new RuntimeException("File is empty");
    }

    if (!"application/pdf".equals(file.getContentType())) {
        throw new RuntimeException("Only PDF files are allowed");
    }
    
    User user = SecurityUtils.getCurrentUser();
    String storedName;
    try {
        storedName = fileStorageUtil.saveFile(file);
    } catch (IOException e) {
        throw new RuntimeException("File upload failed", e);
    }

    FileDocument document =
        FileDocument.builder()
                .originalName(file.getOriginalFilename())
                .storedName(storedName)
                .contentType(file.getContentType())
                .size(file.getSize())
                .filePath("uploads/" + storedName)
                .status(UploadStatus.UPLOADED)
                .user(user)
                .build();
    FileDocument savedFile = fileRepository.save(document);
    return new FileUploadResponse(
            savedFile.getId(),
            savedFile.getOriginalName(),
            savedFile.getStatus(),
            "File upload succesfully"       
    );
   }

}
