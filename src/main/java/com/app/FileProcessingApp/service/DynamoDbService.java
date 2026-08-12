package com.app.FileProcessingApp.service;

import com.app.FileProcessingApp.model.FileMetaData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DynamoDbService {

    @Autowired
    private DynamoDbClient dynamoDbClient;

    private  String tablename = "FileMetaData";

    public void saveMetaData(FileMetaData fileMetaData){
         try{
             Map<String, AttributeValue> item = new HashMap<>();

             item.put("fileId", AttributeValue.builder().s(fileMetaData.getFileId()).build());
             item.put("fileName", AttributeValue.builder().s(fileMetaData.getFileName()).build());
             item.put("s3Key", AttributeValue.builder().s(fileMetaData.getS3Key()).build());
             item.put("contentType", AttributeValue.builder().s(fileMetaData.getContentType()).build());
             item.put("fileSize", AttributeValue.builder().n(String.valueOf(fileMetaData.getFileSize())).build());
             item.put("uploadedAt", AttributeValue.builder().s(fileMetaData.getUploadedAt()).build());
             item.put("status", AttributeValue.builder().s(fileMetaData.getStatus()).build());
             PutItemRequest putItemRequest = PutItemRequest.builder().tableName(tablename).item(item).build();
             log.info("File Meta Data uploaded {}");
             dynamoDbClient.putItem(putItemRequest);

         }catch (Exception ex){
             throw new RuntimeException("Failed to save file Metadata " + ex.getMessage() );
         }
    }
}
