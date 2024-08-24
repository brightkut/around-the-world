package com.brightkut.aroundtheworld.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DocumentService {
    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);
    private final PgVectorStore vectorStore;
    private final FileService fileService;

    public DocumentService(PgVectorStore vectorStore, FileService fileService) {
        this.vectorStore = vectorStore;
        this.fileService = fileService;
    }

    public String addDocument(MultipartFile file) {
        String originalFileName = fileService.upload(file);
        String fileType = originalFileName.split("\\.")[1];

        Resource resource = new ClassPathResource("data-set/"+originalFileName);

        if (fileType.equals("txt")) {
            TextReader textReader = new TextReader(resource);
            // add metadata
            textReader.getCustomMetadata().put("filename", originalFileName);
            log.info("Add metadata to document");
            // get document from txt
            List<Document> documents = textReader.get();
            //RecursiveCharacterTextSplitter
            // Split doc to chunk
            TokenTextSplitter splitter = new TokenTextSplitter();
            List<Document> splitDocuments = splitter.split(documents);

            // add to vector store
            vectorStore.add(splitDocuments);
            log.info("Loaded documents filename: {} ,file_type: {} to vector store successfully", originalFileName, fileType);
        }
        return originalFileName;
    }
}
