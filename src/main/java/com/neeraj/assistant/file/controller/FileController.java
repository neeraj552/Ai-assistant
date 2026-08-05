package com.neeraj.assistant.file.controller;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.neeraj.assistant.file.dto.FileDownloadResponse;
import com.neeraj.assistant.file.dto.FileResponse;
import com.neeraj.assistant.file.dto.FileUploadResponse;
import com.neeraj.assistant.file.service.FileService;
import com.neeraj.assistant.file.entity.FileSortType;

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

            
    return ResponseEntity.ok(

            fileService.uploadFile(file)

    );
}

@GetMapping
public ResponseEntity<List<FileResponse>> getMayFiles(){
    return ResponseEntity.ok(fileService.getMyFiles());
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteFile(@PathVariable UUID id){
    fileService.deleteFile(id);
    return ResponseEntity.noContent().build();
}

@GetMapping("/download/{id}")
public ResponseEntity<Resource> downloadFile(@PathVariable UUID id){

    FileDownloadResponse response =
           fileService.downloadFile(id);
    
    return ResponseEntity.ok()
            .contentType(
                    MediaType.parseMediaType(response.getContentType()))
            .header(
                    org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" +
                    response.getOriginalFileName() +
                    "\"")
            .body(response.getResource());


}

@GetMapping("/search")
public ResponseEntity<List<FileResponse>> searchFiles(
        @RequestParam String keyword
) {
    return ResponseEntity.ok(
            fileService.searchFiles(keyword)
    );
}
@GetMapping("/sort")
public ResponseEntity<List<FileResponse>> sortFiles(
        @RequestParam FileSortType by
) {

    return ResponseEntity.ok(
            fileService.sortFiles(by)
    );

}

}
