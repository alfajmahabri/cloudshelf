package com.example.cloudshelf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig {

    @Value("${aws.region}")
    private String regionName;

    @Value("${aws.accessKeyId}")
    private String access;

    @Value("${aws.secretAccessKey}")
    private String secret;

    @Bean
    public S3Client s3Client(){

        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(access,secret);

        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .region(Region.of(regionName))
                .build();

        /* EC 2
        return S3Client.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.of(regionName))
                .build();
         */
    }
}