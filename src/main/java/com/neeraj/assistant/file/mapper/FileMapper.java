package com.neeraj.assistant.file.mapper;

import com.neeraj.assistant.file.dto.FileResponse;
import com.neeraj.assistant.file.entity.FileDocument;

public class FileMapper {

    private FileMapper() {
    }

    public static FileResponse toResponse(FileDocument file) {

        return FileResponse.builder()
                .id(file.getId())
                .originalName(file.getOriginalName())
                .fileSize(file.getSize())
                .status(file.getStatus())
                .uploadedAt(file.getUploadAt())
                .build();
    }
}