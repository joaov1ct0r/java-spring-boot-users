package com.joaov1ct0r.restful_api_users_java.modules.domain.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class S3Service extends BaseService {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public String uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        this.s3Client.putObject(new PutObjectRequest(this.bucketName, fileName, fileObj));
        fileObj.delete();
        return "https://" + this.bucketName + ".s3.us-east-1.amazonaws.com/" + fileName;
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            System.err.println("Error converting multipartFile to file" + e.getMessage());
        }
        return convertedFile;
    }
}