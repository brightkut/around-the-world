package com.brightkut.aroundtheworld.controller;

import com.brightkut.aroundtheworld.service.FileService;
import com.brightkut.aroundtheworld.validation.ValidFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    ResponseEntity<String> uploadFile(@ValidFile @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(fileService.upload(file));
    }
}
