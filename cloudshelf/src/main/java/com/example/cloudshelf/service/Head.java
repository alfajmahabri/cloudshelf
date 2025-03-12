package com.example.cloudshelf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;

public class Head {

    @Value("${aws.bucketName}")
    private String bucketName;

    @Autowired
    private S3Client s3Client;

    public boolean checkFile(String key){
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3Client.headObject(headObjectRequest);
            return  true;
        } catch (AwsServiceException e) {
            return false;
        }
    }
    
}
