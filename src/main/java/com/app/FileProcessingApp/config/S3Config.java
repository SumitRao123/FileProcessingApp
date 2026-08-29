package com.app.FileProcessingApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
@Profile("dev")
public class S3Config {



     @Bean
     public S3Client s3Client(){
         return S3Client.create();
     }

     @Bean
    public DynamoDbClient dynamoDbClient(){

         return DynamoDbClient.create();
     }

     @Bean
     public SqsClient sqsClient(){
         return SqsClient.create();
     }
}
