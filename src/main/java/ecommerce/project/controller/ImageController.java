package ecommerce.project.controller;

import ecommerce.project.dto.ImageResponse;
import ecommerce.project.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequestMapping("/api/v1/upload/")
@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    @PostMapping("/product/{Id}")
    public ResponseEntity<ImageResponse> upload(
            @PathVariable UUID Id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        ImageResponse image = imageService.UploadProduct(Id,file);

        return ResponseEntity.ok(image);
    }

    @PostMapping("/profile/{id}")
    public ResponseEntity<ImageResponse> uploadImage(
            @PathVariable ("id") UUID Id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        ImageResponse image = imageService.UploadProfile(Id,file);

        return ResponseEntity.ok(image);
    }

}
