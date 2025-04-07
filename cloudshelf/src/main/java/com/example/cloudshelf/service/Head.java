package com.example.cloudshelf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

import java.util.ArrayList;
import java.util.List;

@Service
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

    public List<String> listObjects() {
        ListObjectsV2Request listreq = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();
        ListObjectsV2Iterable list = s3Client.listObjectsV2Paginator(listreq);

        List<String> keys = new ArrayList<>();
        for (ListObjectsV2Response page : list) {
            for (S3Object s3Object : page.contents()) {
                keys.add(s3Object.key());
            }
        }
        return keys;
    }
}
