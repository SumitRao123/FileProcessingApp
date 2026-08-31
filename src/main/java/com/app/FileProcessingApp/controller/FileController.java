package com.app.FileProcessingApp.controller;

import com.app.FileProcessingApp.dto.ApiResponseWrapper;
import com.app.FileProcessingApp.service.S3Service;
import com.app.FileProcessingApp.service.SqsConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private SqsConsumerService service;

    @PostMapping("/uploads")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile){
        String fileId = s3Service.upload(multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                "File uploaded" + fileId
        );
    }

    @GetMapping("/health")
    public String getHealth()
    {
         return "UP";
    }
}
