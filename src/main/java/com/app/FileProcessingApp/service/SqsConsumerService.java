package com.app.FileProcessingApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Service
public class SqsConsumerService {

    @Autowired
    private SqsClient sqsClient;

    private String queueUrl = ("https://sqs.ap-south-1.amazonaws.com/332040500916/file-processing-queue.fifo");

    public void processMessage(){
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .waitTimeSeconds(10)
                .build();

        List<Message> messages =
                sqsClient.receiveMessage(request).messages();

        for (Message message : messages) {

            System.out.println(
                    "Received SQS message: "
                            + message.body()
            );


            // TODO:
            // 1. Parse bucket/key
            // 2. Download file from S3
            // 3. Process file
            // 4. Save result to DynamoDB


        }
    }

}
