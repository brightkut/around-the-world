package com.brightkut.aroundtheworld.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtil {

    public static void saveFile(String fileName, String directoryPath, MultipartFile multipartFile)
            throws IOException {
        Path uploadPath = Paths.get(directoryPath);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath;
        try (InputStream inputStream = multipartFile.getInputStream()) {
            filePath = uploadPath.resolve( fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }

    }
}
