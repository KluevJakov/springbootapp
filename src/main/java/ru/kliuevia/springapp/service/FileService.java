package ru.kliuevia.springapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class FileService {

    @Value("${storage.path}")
    private String STORAGE_PATH;

    public void save(MultipartFile file) {
        String filename = file.getOriginalFilename();

        if (filename == null) {
            throw new IllegalArgumentException("Название файла не может быть пустым!");
        }

        int indexOfDot = filename.lastIndexOf('.');

        if (indexOfDot == -1) {
            throw new IllegalArgumentException("Файл должен иметь расширение!");
        }

        String ext = filename.substring(indexOfDot);

        if (!".pdf".equals(ext)) {
            throw new IllegalArgumentException("Файл должен иметь расширение .pdf!");
        }

        Path storagePath = Paths.get(STORAGE_PATH);
        Path destinationPath = storagePath.resolve(filename);

        try (OutputStream fos = new FileOutputStream(destinationPath.toFile())) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Ошибка записи файла {}", e.getMessage());
        }
    }


    public Resource get(String filename) {
        if (filename.contains("..")) {
            throw new IllegalArgumentException("Ошибка в запросе файла");
        }

        Path storagePath = Paths.get(STORAGE_PATH);
        Path destinationPath = storagePath.resolve(filename);

        try {
            return new UrlResource(destinationPath.toUri());
        } catch (Exception e) {
            return null;
        }
    }
}
