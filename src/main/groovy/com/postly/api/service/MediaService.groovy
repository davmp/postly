package com.postly.api.service

import com.amazonaws.services.s3.AmazonS3
import jakarta.validation.constraints.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

import java.nio.charset.StandardCharsets

@Service
class MediaService {
    @Autowired
    AmazonS3 s3Client

    @Value("\${aws.bucket.name}")
    String bucketName

    String uploadFile(@NotNull MultipartFile image) {
        String fileName = UUID.randomUUID().toString() + "-" + image.getOriginalFilename()

        try {
            File file = convertMultipartToTile(image)
            s3Client.putObject(bucketName, fileName, file)
            file.delete()
            return s3Client.getUrl(bucketName, fileName).toString()
        } catch (Exception e) {
            System.out.println("Failed to upload file to S3: " + e)
            return ""
        }
    }

    void deleteFile(@NotNull String fileUrl) {
        try {
            s3Client.deleteObject(bucketName, getFileNameByUrl(fileUrl))
        } catch (Exception e) {
            System.out.println("Failed to delete file from S3: " + e)
        }
    }

    private static String getFileNameByUrl(String fileUrl) {
        def decodedUrl = URLDecoder.decode(fileUrl, StandardCharsets.UTF_8.name())

        return decodedUrl.tokenize("/").last()
    }

    private static File convertMultipartToTile(@NotNull MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()))
        FileOutputStream fos = new FileOutputStream(file)
        fos.write(multipartFile.getBytes())
        fos.close()
        return file
    }
}
