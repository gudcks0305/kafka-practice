package com.example.kafkapractice.presigned;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;

 class PreSignedUploadTest {
    @Test
    void tst(){
        upload("https://presigned-test-e.s3.ap-northeast-2.amazonaws.com/test-prefix/test-file-name?x-amz-acl=public-read-write&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230504T131253Z&X-Amz-SignedHeaders=host&X-Amz-Expires=1199&X-Amz-Credential=AKIAYT3DWJBHNMDE4U65/20230504/ap-northeast-2/s3/aws4_request&X-Amz-Signature=b64a86e1e8cf3a084e9886ebd5856e90b0a4881f26c9de68391fd8349752c130"
        ,new File("C:\\Project\\practice\\kafka-practice\\src\\test\\java\\com\\example\\kafkapractice\\presigned\\PreSignUploadTest.java")
        );
    }
   public String upload(String preSignedUrl, File file) {
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_PDF);
        preSignedUrl = preSignedUrl.replace("%2F" , "/");
       System.out.println(preSignedUrl);
       HttpEntity<Resource> entity = new HttpEntity<>(new FileSystemResource(file), headers);
       ResponseEntity<String> response = new RestTemplate().exchange(preSignedUrl, HttpMethod.PUT, entity, String.class);
       System.out.println(response.getStatusCode());
       System.out.println(response.getHeaders());
       return response.getBody();
   }
}
