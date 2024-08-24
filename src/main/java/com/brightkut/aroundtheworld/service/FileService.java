package com.brightkut.aroundtheworld.service;

import com.brightkut.aroundtheworld.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
public class FileService {
    private static final String UPLOAD_DIR = "src/main/resources/data-set/";
    private static final Logger log = LoggerFactory.getLogger(FileService.class);

    public String upload(MultipartFile file) {
        try {
            // Normalize the file name
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            FileUtil.saveFile(originalFileName, UPLOAD_DIR, file);

            return "File uploaded successfully: " + originalFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }
}
