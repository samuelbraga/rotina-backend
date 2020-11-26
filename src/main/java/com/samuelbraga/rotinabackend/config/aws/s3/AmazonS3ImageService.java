package com.samuelbraga.rotinabackend.config.aws.s3;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.models.Image;
import com.samuelbraga.rotinabackend.repositories.ImageRepository;
import com.samuelbraga.rotinabackend.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AmazonS3ImageService extends AmazonS3ClientService {

  private final ImageRepository imageRepository;

  @Autowired
  public AmazonS3ImageService(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  public Image insertImages(
    MultipartFile multipartFile,
    UUID id,
    String folder
  ) {
    return uploadImageToAmazon(multipartFile, id, folder);
  }

  public Image uploadImageToAmazon(
    MultipartFile multipartFile,
    UUID id,
    String folder
  ) {
    List<String> validExtensions = Arrays.asList("jpeg", "jpg", "png");

    String extension = FilenameUtils.getExtension(
      multipartFile.getOriginalFilename()
    );
    if (!validExtensions.contains(extension)) {
      throw new BaseException("Image invalid format");
    } else {
      String url = uploadMultipartFile(multipartFile, id, folder);

      Image image = new Image();
      image.setImage_url(url);

      return imageRepository.save(image);
    }
  }

  public void removeImageFromAmazon(Image image) {
    String fileName = image
      .getImage_url()
      .substring(image.getImage_url().lastIndexOf("/") + 1);
    getClient()
      .deleteObject(new DeleteObjectRequest(getBucketName(), fileName));
    imageRepository.delete(image);
  }

  private String uploadMultipartFile(
    MultipartFile multipartFile,
    UUID id,
    String folder
  ) {
    String fileUrl;

    try {
      File file = FileUtils.convertMultipartToFile(multipartFile);
      String extension = FilenameUtils.getExtension(
        multipartFile.getOriginalFilename()
      );
      String fileName = folder + id + "." + extension;

      uploadPublicFile(fileName, file);

      boolean delete = file.delete();

      if (!delete) {
        throw new BaseException("Current error on delete file");
      }

      fileUrl = getUrl().concat(fileName);
    } catch (IOException e) {
      throw new BaseException("Current error on convert file");
    }

    return fileUrl;
  }

  private void uploadPublicFile(String fileName, File file) {
    getClient()
      .putObject(
        new PutObjectRequest(getBucketName(), fileName, file)
        .withCannedAcl(CannedAccessControlList.PublicRead)
      );
  }
}
