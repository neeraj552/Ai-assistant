package com.neeraj.assistant.file.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.neeraj.assistant.file.dto.FileDownloadResponse;
import com.neeraj.assistant.file.dto.FileResponse;
import com.neeraj.assistant.file.dto.FileUploadResponse;
import com.neeraj.assistant.file.entity.FileSortType;


public interface FileService {

    FileUploadResponse uploadFile(MultipartFile file);

    List<FileResponse> getMyFiles();
    
    void deleteFile(UUID fileId);
    
    FileDownloadResponse downloadFile(UUID id);

    List<FileResponse> searchFiles(String keyword);
    List<FileResponse> sortFiles(FileSortType sortType);

}
