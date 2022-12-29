package com.ss.parlour.articleservice.handler.image.aws;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Component
public class AwsImageHandler implements AwsImageHandlerI {

    //@Autowired
    private AmazonS3 amazonS3;

   // @Value("${s3.bucket.name}")
    private String s3BucketName;


    @Override
    public String generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean){
        String fileName = UUID.randomUUID().toString() + preSignUrlGenerateRequestBean.getExtension();
        return generatePreSignedUrl(fileName, HttpMethod.PUT);
    }

    public String findFileByName(PreSignUrlRequestBean preSignUrlRequestBean) {
        if (!amazonS3.doesObjectExist(s3BucketName, preSignUrlRequestBean.getFileName()))
            return "File does not exist";
        return generatePreSignedUrl(preSignUrlRequestBean.getFileName(), HttpMethod.GET);
    }

    public String generatePreSignedUrl(String filePath, HttpMethod httpMethod) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10); //validity of 10 minutes
        return amazonS3.generatePresignedUrl(s3BucketName, filePath, calendar.getTime(), httpMethod).toString();
    }


}
