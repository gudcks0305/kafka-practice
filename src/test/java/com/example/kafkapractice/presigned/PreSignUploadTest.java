package com.example.kafkapractice.presigned;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.net.URL;
import java.util.Date;
@SpringBootTest
@Import(PreSignUploadTest.AWSConfig.class)
@TestPropertySource(locations = "classpath:application-s3.yml")
class PreSignUploadTest {
    @Autowired
    private AmazonS3 amazonS3;

    @Test
    @DisplayName("PreSignUploadTest")
    void preSignUploadTest() {
        String bucket = "presigned-test-e";
        String prefix = "test-prefix";
        String fileName = "test-file-name";
        String preSignedUrl = getPreSignedUrl(bucket, prefix, fileName);
        System.out.println(preSignedUrl);
        PreSignedUploadTest preSignedUploadTest = new PreSignedUploadTest();
        String result = preSignedUploadTest.upload(preSignedUrl, new File("C:\\Project\\practice\\kafka-practice\\src\\test\\java\\com\\example\\kafkapractice\\presigned\\PreSignUploadTest.java"));
        System.out.println("result = " + result);
    }

    public String getPreSignedUrl(String bucket, String prefix, String fileName) {
        if (!prefix.equals("")) {
            fileName = prefix + "/" + fileName;
        }
        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(bucket, fileName);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucket, String fileName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(getPreSignedUrlExpiration());
        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.Private.toString());
        return generatePresignedUrlRequest;
    }

    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 20;
        expiration.setTime(expTimeMillis);
        System.out.println(expiration);
        return expiration;
    }


    @Configuration
    @EnableConfigurationProperties
    public static class AWSConfig {

        @Value("${cloud.aws.credentials.access-key:awskey}")
        public String accessKey;
        @Value("${cloud.aws.credentials.secret-key:awssecret}")
        public String secretKey;
        @Value("${cloud.aws.region.static:ap-northeast-2}")
        public String region;

        @Bean
        @Primary
        public AWSCredentials awsCredentialsProvider() {
            return new BasicAWSCredentials(accessKey, secretKey);
        }

        @Bean
        public AmazonS3 amazonS3(AWSCredentials awsCredentialsProvider) {
            AmazonS3 s3Builder = AmazonS3ClientBuilder.standard()
                    .withRegion(region)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentialsProvider))
                    .build();
            return s3Builder;
        }
    }
}
