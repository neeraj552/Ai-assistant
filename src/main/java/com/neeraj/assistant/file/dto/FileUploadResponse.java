package com.neeraj.assistant.file.dto;

import java.util.UUID;

import com.neeraj.assistant.file.entity.UploadStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileUploadResponse {
    private UUID id;
    private String fileName;
    private UploadStatus status;
    private String message;
}
