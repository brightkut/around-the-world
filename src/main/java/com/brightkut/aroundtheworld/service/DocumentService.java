package com.brightkut.aroundtheworld.service;

import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    private final PgVectorStore vectorStore;

    public DocumentService(PgVectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }
}
