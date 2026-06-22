package com.neeraj.assistant.file.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileStorageUtil fileStorageUtil;

    @Override
    public FileUploadResponse uploadFile(MultipartFile file) {

        validateFile(file);

        User user = SecurityUtils.getCurrentUser();

        String storedName = storeFile(file);

        FileDocument document = buildFileDocument(file, storedName, user);

        FileDocument savedFile = fileRepository.save(document);

        return buildResponse(savedFile);
    }



    private void validateFile(MultipartFile file) {

        if(file.isEmpty()){
            throw new RuntimeException("File is Empty");
        }

        if (!"application/pdf".equals(file.getContentType())) {
        throw new RuntimeException("Only PDF files are allowed");
        }
    }



    private String storeFile(MultipartFile file) {

        try{
            return fileStorageUtil.saveFile(file);
        } catch(IOException e){
            throw new RuntimeException("File Upload failed", e);
        }

    }



    private FileDocument buildFileDocument(
            MultipartFile file,
            String storedName,
            User user) {

                return FileDocument.builder()
                        .originalName(file.getOriginalFilename())
                        .storedName(storedName)
                        .contentType(file.getContentType())
                        .size(file.getSize())
                        .filePath("uploads/" + storedName)
                        .status(UploadStatus.UPLOADED)
                        .user(user)
                        .build();

    }



    private FileUploadResponse buildResponse(FileDocument fileDocument) {

    return new FileUploadResponse(
            fileDocument.getId(),
            fileDocument.getOriginalName(),
            fileDocument.getStatus(),
            "File uploaded successfully"
    );

    }
}
