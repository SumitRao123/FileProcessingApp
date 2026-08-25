package com.app.FileProcessingApp.service;

import com.app.FileProcessingApp.model.S3FileMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

@Service
public class SqsConsumerService {

    @Autowired
    private SqsClient sqsClient;

    @Autowired
    public S3Service s3Service;
    @Autowired
    private ObjectMapper objectMapper;

    private String queueUrl = ("https://sqs.ap-south-1.amazonaws.com/332040500916/file-processing-queue.fifo");

    @SqsListener("file-processing-queue.fifo")
    public void processMessage(Message message) throws JsonProcessingException {


        System.out.println(message.body());
            // TODO:
            // 1. Parse bucket/key
            S3FileMessage s3FileMessage = objectMapper.readValue(message.body(),S3FileMessage.class);
            String objectName = URLDecoder.decode(s3FileMessage.getKey(), StandardCharsets.UTF_8);
        System.out.println(objectName);
            Path path = s3Service.download(objectName,s3FileMessage.getBucket());
            System.out.println(path.getFileName());
            // 2. Download file from S3
            // 3. Process file
            // 4. Save result to DynamoDB



    }

}
