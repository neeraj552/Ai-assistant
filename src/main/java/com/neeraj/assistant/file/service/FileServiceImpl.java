package com.neeraj.assistant.file.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.neeraj.assistant.chat.repository.ChatRepository;
import com.neeraj.assistant.common.security.SecurityUtils;
import com.neeraj.assistant.file.dto.FileDownloadResponse;
import com.neeraj.assistant.file.dto.FileResponse;
import com.neeraj.assistant.file.dto.FileUploadResponse;
import com.neeraj.assistant.file.entity.FileDocument;
import com.neeraj.assistant.file.entity.FileSortType;
import com.neeraj.assistant.file.entity.UploadStatus;
import com.neeraj.assistant.file.exception.FileStorageException;
import com.neeraj.assistant.file.exception.InvalidFileException;
import com.neeraj.assistant.file.exception.ResourceNotFoundException;
import com.neeraj.assistant.file.mapper.FileMapper;
import com.neeraj.assistant.file.repository.FileRepository;
import com.neeraj.assistant.file.util.FileStorageUtil;
import com.neeraj.assistant.rag.repository.DocumentChunkRepository;
import com.neeraj.assistant.rag.service.ChunkService;
import com.neeraj.assistant.summary.repository.SummaryRepository;
import com.neeraj.assistant.user.entity.User;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileStorageUtil fileStorageUtil;
    private final ChunkService    chunkService;
    private final SummaryRepository summaryRepository;
    private final ChatRepository    chatRepository;
    private final DocumentChunkRepository documentChunkRepository;

    @Override
    public FileUploadResponse uploadFile(MultipartFile file) {

        validateFile(file);

        User user = SecurityUtils.getCurrentUser();

        String storedName = storeFile(file);

        FileDocument document = buildFileDocument(file, storedName, user);

        FileDocument savedFile = fileRepository.save(document);
        
        chunkService.processDocument(savedFile.getId());

        return buildResponse(savedFile);
    }



    private void validateFile(MultipartFile file) {

        if(file.isEmpty()){
            throw new InvalidFileException("File is Empty");
        }

        if (!"application/pdf".equals(file.getContentType())) {
        throw new InvalidFileException("Only PDF files are allowed");
        }
    }



    private String storeFile(MultipartFile file) {

        try{
            return fileStorageUtil.saveFile(file);
        } catch(IOException e){
            throw new FileStorageException("File Upload failed", e);
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

    
    @Override
    public List<FileResponse> getMyFiles() {

    User user = SecurityUtils.getCurrentUser();

    return fileRepository.findByUser(user)
            .stream()
            .map(FileMapper::toResponse)
            .toList();
    }
    
    @Override
    public void deleteFile(UUID fileId){
        User user = SecurityUtils.getCurrentUser();

        FileDocument file = fileRepository
                .findByIdAndUser(fileId, user)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));

    documentChunkRepository.deleteByFile(file);

 
    summaryRepository.findByFile(file)
            .ifPresent(summaryRepository::delete);


    chatRepository.deleteByFileAndUser(file, user);


        try{
            fileStorageUtil.deleteFile(file.getStoredName());
        } catch(IOException e){
            throw new FileStorageException("Failed to delete file", e);
        }        

        fileRepository.delete(file);
    }

    @Override
    public FileDownloadResponse downloadFile(UUID id){
        User user = SecurityUtils.getCurrentUser();

        FileDocument file = fileRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));

            Resource resource =
                fileStorageUtil.loadFile(file.getStoredName());

            return new FileDownloadResponse(
                  resource, 
                  file.getOriginalName(),
                  file.getContentType()
                );       
    }
    @Override
    public List<FileResponse> searchFiles(String keyword){

        User user = SecurityUtils.getCurrentUser();

        return fileRepository
            .findByUserAndOriginalNameContainingIgnoreCase(user, keyword)
            .stream()
            .map(FileMapper::toResponse)
            .toList();

    }

    public List<FileResponse> sortFiles(FileSortType sortType) {

    User user = SecurityUtils.getCurrentUser();

    List<FileDocument> files = switch (sortType) {
    case NEWEST -> fileRepository.findByUserOrderByUploadAtDesc(user);
    case OLDEST -> fileRepository.findByUserOrderByUploadAtAsc(user);
    case NAME_ASC -> fileRepository.findByUserOrderByOriginalNameAsc(user);
    case NAME_DESC -> fileRepository.findByUserOrderByOriginalNameDesc(user);
    case SIZE_ASC -> fileRepository.findByUserOrderBySizeAsc(user);
    case SIZE_DESC -> fileRepository.findByUserOrderBySizeDesc(user);
    };

    return files.stream()
            .map(FileMapper::toResponse)
            .toList();
    }

}
