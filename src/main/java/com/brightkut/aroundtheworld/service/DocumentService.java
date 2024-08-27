package com.brightkut.aroundtheworld.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.PgVectorStore;
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

        if (fileType.equals("txt")) {
            TextReader textReader = new TextReader(file.getResource());
            // add metadata
            textReader.getCustomMetadata().put("filename", originalFileName);
            log.info("Add metadata to document");
            // get document from txt
            List<Document> documents = textReader.get();
            //RecursiveCharacterTextSplitter
            // Split doc to chunk
            TokenTextSplitter splitter = new TokenTextSplitter();
            List<Document> splitDocuments = splitter.split(documents);

            //add metadata
            for (Document split : splitDocuments) {
                split.getMetadata().put("filename", originalFileName);
                split.getMetadata().put("file_type", fileType);
                split.getMetadata().put("version", 1);
            }


            // add to vector store
            vectorStore.add(splitDocuments);
            log.info("Loaded documents filename: {} ,file_type: {} to vector store successfully", originalFileName, fileType);
        }else if(fileType.equals("pdf")){

            PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(file.getResource(),
                    PdfDocumentReaderConfig.builder()
                            .withPageTopMargin(0)
                            .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                    .withNumberOfTopTextLinesToDelete(0)
                                    .build())
                            .withPagesPerDocument(1)
                            .build());
            // get document from pdf
            List<Document> documents = pdfReader.read();
            //RecursiveCharacterTextSplitter
            // Split doc to chunk
            TokenTextSplitter splitter = new TokenTextSplitter();
            List<Document> splitDocuments = splitter.split(documents);
            //add metadata
            for (Document split : splitDocuments) {
                split.getMetadata().put("filename", originalFileName);
                split.getMetadata().put("file_type", fileType);
                split.getMetadata().put("version", 1);
            }

            // add to vector store
            vectorStore.add(splitDocuments);
        }
        return originalFileName;
    }
}
