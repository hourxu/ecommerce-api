package ecommerce.project.service.impl;

import ecommerce.project.dto.ImageResponse;
import ecommerce.project.entity.Image;
import ecommerce.project.entity.Product;
import ecommerce.project.entity.Profile;
import ecommerce.project.exception.ProductNotFoundException;
import ecommerce.project.respositity.ImageRepository;
import ecommerce.project.respositity.ProductRepository;
import ecommerce.project.respositity.ProfileRepository;
import ecommerce.project.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectAclRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.awt.desktop.ScreenSleepEvent;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
//==========R2 it mean cloud flare storge
public class ImageServiceImpL implements ImageService {
    private final S3Client s3Client;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ProfileRepository profileRepository;
    @Value("${cloudflare.r2.bucket}")
    private String bucket;

    @Override
    public ImageResponse UploadProduct(UUID Id, MultipartFile file) throws IOException {
        Product product=productRepository.findById(Id).orElseThrow(ProductNotFoundException::new);

        String fileName= UUID.randomUUID()+"-"+file.getOriginalFilename();//generate id of image
        String key="product/"+fileName;//create image location and store id of image into product

        PutObjectRequest request=PutObjectRequest.builder()
                .bucket(bucket)//put request into bucket
                .key(key)//tell R2 name path (product)
                .contentType(file.getContentType()).build();//input type file

        s3Client.putObject(
                request, RequestBody.fromBytes(file.getBytes())
        );//upload image file into cloud flare

        Image image=new Image();
        //put data into db
        image.setImageUrl(key);
        image.setProduct(product);
        imageRepository.save(image);

        return new ImageResponse(
                image.getId(),
                image.getImageUrl()
        );
    }
    public ImageResponse UploadProfile(UUID Id, MultipartFile file) throws IOException {
        Profile profile=profileRepository.findById(Id).orElseThrow(ProductNotFoundException::new);

        String fileName= UUID.randomUUID()+"-"+file.getOriginalFilename();//generate id of image

        String key="profile/"+fileName;//create image location and store id of image into product

        PutObjectRequest request=PutObjectRequest.builder()
                .bucket(bucket)//put request into bucket
                .key(key)//tell R2 name path (product)
                .contentType(file.getContentType()).build();//input type file

        s3Client.putObject(
                request, RequestBody.fromBytes(file.getBytes())
        );//upload image file into cloud flare

        profile.setProfileImage(key);
        profileRepository.save(profile);

        return new ImageResponse(
                profile.getId(),
                profile.getProfileImage()
        );
    }
}
