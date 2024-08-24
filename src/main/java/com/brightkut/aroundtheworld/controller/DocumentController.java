package com.brightkut.aroundtheworld.controller;

import com.brightkut.aroundtheworld.service.DocumentService;
import com.brightkut.aroundtheworld.validation.ValidFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    ResponseEntity<String> addDocument(@ValidFile @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok("Documents name "+ documentService.addDocument(file)+ " added successfully");
    }
}
