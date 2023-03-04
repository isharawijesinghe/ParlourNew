package com.ss.parlour.articleservice.handler.cloud;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlGenerateRequest;
import com.ss.parlour.articleservice.utils.bean.response.PreSignUrlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class AWSArticleHandler implements CommonCloudHandlerI{

    @Value("${aws.bucket.user-image-upload}")
    private String bucketName;

    @Override
    public PreSignUrlResponse generatePreSignUrl(PreSignUrlGenerateRequest preSignUrlGenerateRequest){
        PreSignUrlResponse preSignUrlResponse = new PreSignUrlResponse();
        String objectKey = preSignUrlGenerateRequest.getExtension();
        AWSCredentialsProvider provider = new DefaultAWSCredentialsProviderChain();
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(provider).build();

        // Set the pre signed URL to expire after 10 minutes.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 10;
        expiration.setTime(expTimeMillis);

        // Generate the pre signed URL.
        System.out.println("Generating pre-signed URL......");
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        preSignUrlResponse.setPreSignUrl(url);
        return preSignUrlResponse;
    }
}
