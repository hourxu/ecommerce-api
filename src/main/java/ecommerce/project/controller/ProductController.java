package ecommerce.project.controller;

import ecommerce.project.dto.ImageResponse;
import ecommerce.project.dto.TestRequest;
import ecommerce.project.dto.product.ProductDetail;
import ecommerce.project.dto.product.ProductRequest;
import ecommerce.project.dto.product.ProductResponse;
import ecommerce.project.entity.Image;
import ecommerce.project.service.ImageService;
import ecommerce.project.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.EOFException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ImageService imageServicel;
    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest request){
        return new ResponseEntity<>(
                productService.createProduct(request),
                HttpStatus.CREATED
        );
    }
    //update
    //id product
    @PutMapping("{id}")
    public ResponseEntity<ProductResponse>update(@PathVariable UUID id,@RequestBody ProductRequest request){
        return new ResponseEntity<>(
                productService.updateProduct(id,request),
                HttpStatus.OK
        );
    }
    //filter product
    @PostMapping("/filter")
    public ResponseEntity<List<ProductResponse>>getTest(@RequestBody TestRequest request){
      return ResponseEntity.ok(productService.getByCategoryId(request.categoryId()));
    }
    // get all item of product
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll(
            ){

        return new ResponseEntity<>(
                productService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDetail>productDetail(@PathVariable UUID id){
        return new ResponseEntity<>(
                productService.getById(id),
                HttpStatus.OK

        );
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?>deleteById(@PathVariable UUID id){
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
