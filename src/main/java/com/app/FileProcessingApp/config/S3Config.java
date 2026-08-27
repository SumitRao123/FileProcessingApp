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

     @Value("${aws.s3.access_key}")
     private String access_key;

     @Value("${aws.s3.secret_key}")
     private  String secret_key;

     @Value("${aws.s3.region}")
    private  String region;

     @Bean
     public S3Client s3Client(){
         AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(access_key,secret_key);
         return S3Client.builder().region(Region.of(region)).
                 credentialsProvider(StaticCredentialsProvider.
                         create(awsBasicCredentials)).build();
     }

     @Bean
    public DynamoDbClient dynamoDbClient(){
         AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(access_key,secret_key);
         return DynamoDbClient.builder().region(Region.of(region)).credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials)).build();
     }

     @Bean
     public SqsClient sqsClient(){
         AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(access_key,secret_key);
         return SqsClient.builder().region(Region.of(region)).credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials)).build();
     }
}
