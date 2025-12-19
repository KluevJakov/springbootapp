package ru.kliuevia.springapp.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.kliuevia.springapp.controller.FileController;
import ru.kliuevia.springapp.service.FileService;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileControllerImpl implements FileController {

    private final FileService fileService;

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void upload(@RequestParam("file") MultipartFile file) {
        fileService.save(file);
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> download(@PathVariable("filename") String filename) {
        Resource resource = fileService.get(filename);

        if (resource == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }
}
