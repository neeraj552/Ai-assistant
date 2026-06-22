 package com.neeraj.assistant.file.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileStorageUtil {
    @Value("${file.upload-dir}")
    private  String UPLOAD_DIR;

    public String saveFile(MultipartFile file) throws IOException{

        Path uploadPath = Paths.get(UPLOAD_DIR);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
            
        }

        String extension = getExtension(file.getOriginalFilename());

        String storedName = UUID.randomUUID() + extension;

        Path target = uploadPath.resolve(storedName);

        Files.copy(file.getInputStream(), target);

        return storedName;
    }
    
    public void deleteFile(String storedName) throws IOException{
        Path filePath = Paths.get(UPLOAD_DIR, storedName);

        Files.deleteIfExists(filePath);
    }

    private String getExtension(String fileName){

        if(fileName  == null || !fileName.contains(".")){
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

}
