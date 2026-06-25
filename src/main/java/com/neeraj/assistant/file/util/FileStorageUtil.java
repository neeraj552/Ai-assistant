 package com.neeraj.assistant.file.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.neeraj.assistant.file.exception.FileStorageException;

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

    public Resource loadFile(String storedName){
        
        try{
             Path path = Paths.get(UPLOAD_DIR)
                   .resolve(storedName);

             Resource resource = new UrlResource(path.toUri());
             
             
             if(resource.exists() && resource.isReadable()){
                return resource;
             }

             throw new FileStorageException("File not found");
        } catch(MalformedURLException e){
            throw new FileStorageException("Could not load file", e);
        }
    }

    private String getExtension(String fileName){

        if(fileName  == null || !fileName.contains(".")){
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

}
