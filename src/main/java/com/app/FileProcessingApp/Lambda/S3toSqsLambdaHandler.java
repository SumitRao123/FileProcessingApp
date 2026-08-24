package com.app.FileProcessingApp.Lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class S3toSqsLambdaHandler
        implements RequestHandler<Map<String, Object>, String> {

    private final SqsClient sqsClient = SqsClient.create();

    private final String queueUrl =
            System.getenv("SQS_QUEUE_URL");

    @Override
    public String handleRequest(
            Map<String, Object> event,
            Context context) {

        context.getLogger().log(
                "Received S3 event: " + event
        );

        List<Map<String, Object>> records =
                (List<Map<String, Object>>) event.get("Records");

        for (Map<String, Object> record : records) {

            Map<String, Object> s3 =
                    (Map<String, Object>) record.get("s3");

            Map<String, Object> bucket =
                    (Map<String, Object>) s3.get("bucket");

            Map<String, Object> object =
                    (Map<String, Object>) s3.get("object");

            String bucketName =
                    (String) bucket.get("name");

            String key =
                    (String) object.get("key");

            context.getLogger().log(
                    "Bucket: " + bucketName
            );

            context.getLogger().log(
                    "Key: " + key
            );

            String message = """
        {
          "bucket": "%s",
          "key": "%s"
        }
        """.formatted(bucketName, key);

            SendMessageRequest request =
                    SendMessageRequest.builder()
                            .queueUrl(queueUrl)
                            .messageBody(message)
                            .messageGroupId("file-processing")
                            .messageDeduplicationId(
                                    UUID.randomUUID().toString()
                            )
                            .build();

            sqsClient.sendMessage(request);

            context.getLogger().log(
                    "Message sent to SQS: " + message
            );
        }

        return "SUCCESS";
    }
}
