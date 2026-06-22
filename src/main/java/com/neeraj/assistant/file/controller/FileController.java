package com.neeraj.assistant.file.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.neeraj.assistant.file.dto.FileUploadResponse;
import com.neeraj.assistant.file.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    
    @PostMapping(
        value = "/upload",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
)
public ResponseEntity<FileUploadResponse> upload(

        @RequestParam("file") MultipartFile file) {
    System.out.println("===== FILE CONTROLLER HIT =====");
    
    return ResponseEntity.ok(

            fileService.uploadFile(file)

    );
}

}
