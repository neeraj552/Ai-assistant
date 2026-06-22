package com.neeraj.assistant.file.service;

import org.springframework.web.multipart.MultipartFile;

import com.neeraj.assistant.file.dto.FileUploadResponse;


public interface FileService {

    FileUploadResponse uploadFile(MultipartFile file);

}
