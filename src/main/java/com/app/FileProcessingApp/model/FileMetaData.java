package com.app.FileProcessingApp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMetaData {
    private String fileId;
    private String fileName;
    private String s3Key;
    private String contentType;
    private Long fileSize;
    private String uploadedAt;
    private String status;
}
