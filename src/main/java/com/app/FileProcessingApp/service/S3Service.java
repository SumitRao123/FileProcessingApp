package com.app.FileProcessingApp.service;

import com.app.FileProcessingApp.model.FileMetaData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
public class S3Service {


    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket_name}")
    private String bucketName;

    @Autowired
    private DynamoDbService dynamoDbService;

    public String upload(MultipartFile multipartFile){
        String fileId = UUID.randomUUID().toString() + "" + multipartFile.getOriginalFilename();
        try{
            String s3Key = "files/" + fileId + "/" + multipartFile.getOriginalFilename();
            PutObjectRequest putObjectRequest = PutObjectRequest.
                                                   builder().
                                                    bucket(bucketName)
                    .key(fileId)
                    .contentType(multipartFile.getContentType())
                    .build();
            FileMetaData fileMetaData = FileMetaData.builder().fileId(fileId).
                                  fileName(multipartFile.getOriginalFilename())
                    .s3Key(s3Key).contentType(multipartFile.getContentType())
                    .fileSize(multipartFile.getSize()).uploadedAt(Instant.now().toString())
                    .status("UPLOADED").build();
            dynamoDbService.saveMetaData(fileMetaData);
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(multipartFile.getBytes()));
            log.info("file uploaded {}",fileId);
            return fileId;
        }catch (Exception ex){
            throw  new RuntimeException("file upload failed" +ex.getMessage());
        }
    }
}
