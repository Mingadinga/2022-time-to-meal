package com.team20.t4.common.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team20.t4.common.exception.RequestErrorCode;
import com.team20.t4.common.exception.RequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Util {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadFileV1(String dirName, MultipartFile multipartFile) {
        validateFileExists(multipartFile);

        String fileKey = createUniqueFileName(dirName, multipartFile);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileKey, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new S3FileException(FileErrorCode.FILE_UPLOAD_FAIL);
        }
        return fileKey;
    }

    private String createUniqueFileName(String dirName, MultipartFile multipartFile) {
        String originFileName = multipartFile.getOriginalFilename();
        String s3FileName = UUID.randomUUID() + "-" + originFileName;
        String fileName = dirName + "/" + s3FileName;
        return fileName;
    }

    public String getUrl(String filekey){
        return amazonS3Client.getUrl(bucketName, filekey).toString();
    }

    private void validateFileExists(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new RequestException(RequestErrorCode.EMPTY_FILE);
        }
    }

    public void deleteFile(String fileKey) {
        try {
            amazonS3Client.deleteObject(bucketName, fileKey);
        } catch(AmazonServiceException e) {
            throw new S3FileException(FileErrorCode.FILE_DELETE_FAIL);
        }
    }

    public String updateFile(String fileKey, String dirName, MultipartFile multipartFile) {
        deleteFile(fileKey);
        return uploadFileV1(dirName, multipartFile);
    }
}
