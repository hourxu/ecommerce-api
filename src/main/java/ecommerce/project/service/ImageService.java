package ecommerce.project.service;

import ecommerce.project.dto.ImageResponse;
import ecommerce.project.entity.Image;
import ecommerce.project.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface ImageService {
    ImageResponse UploadProduct(UUID productId, MultipartFile file)throws IOException;
    ImageResponse UploadProfile(UUID Id, MultipartFile file)throws IOException;
}
