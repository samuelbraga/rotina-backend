package com.samuelbraga.rotinabackend.config.aws.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AmazonS3ClientService {
  private AmazonS3 amazonS3;
  
  @Value("${amazon.s3.endpoint}")
  private String url;

  @Value("${amazon.s3.bucket-name}")
  private String bucketName;

  @Value("${amazon.access-key}")
  private String accessKey;

  @Value("${amazon.secret-key}")
  private String secretKey;

  @Value("${amazon.region}")
  private String region;

  protected AmazonS3 getClient() {
    return amazonS3;
  }

  protected String getUrl() {
    return url;
  }

  protected String getBucketName() {
    return bucketName;
  }

  @PostConstruct
  private void init() {

    BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

    this.amazonS3 = AmazonS3ClientBuilder.standard()
      .withRegion(Regions.valueOf(region))
      .withCredentials(new AWSStaticCredentialsProvider(credentials))
      .build();
  }
}
