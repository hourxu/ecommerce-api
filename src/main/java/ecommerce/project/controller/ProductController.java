package ecommerce.project.controller;

import ecommerce.project.dto.TestRequest;
import ecommerce.project.dto.product.ProductDetail;
import ecommerce.project.dto.product.ProductRequest;
import ecommerce.project.dto.product.ProductResponse;
import ecommerce.project.entity.Category;
import ecommerce.project.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest request){
        return new ResponseEntity<>(
                productService.createProduct(request),
                HttpStatus.CREATED
        );
    }
    //update
    @PutMapping("{id}")
    public ResponseEntity<ProductResponse>update(@PathVariable UUID id,@RequestBody ProductRequest request){
        return new ResponseEntity<>(
                productService.updateProduct(id,request),
                HttpStatus.OK
        );
    }
    //filter product
    @PostMapping("/filter")
    public ResponseEntity<List<ProductResponse>>getid(@RequestBody TestRequest request){
      return ResponseEntity.ok(productService.getid(request.categoryId()));
    }
    // get all item of product
    @GetMapping("/all")
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
                productService.productDetail(id),
                HttpStatus.OK

        );
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?>deletedId(@PathVariable UUID id){
        productService.deletedId(id);
        return ResponseEntity.ok().build();
    }
}
