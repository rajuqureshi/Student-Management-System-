package com.models.practiceproject.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceIml implements FileStorageService{
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Override
    public String storeFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir );
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = "";
        int dotIndex = originalFileName.lastIndexOf(".");
        if (dotIndex>0) {
            fileExtension = originalFileName.substring(dotIndex);
        }

        String fileName = UUID.randomUUID() + fileExtension;
        Path targetLocation = uploadPath.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                targetLocation,
                StandardCopyOption.REPLACE_EXISTING
        );

        return fileName;
    }
}
