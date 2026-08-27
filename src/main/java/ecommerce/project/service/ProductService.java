package ecommerce.project.service;

import ecommerce.project.dto.product.ProductDetail;
import ecommerce.project.dto.product.ProductRequest;
import ecommerce.project.dto.product.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(UUID id, ProductRequest request);
    List<ProductResponse>getAll();
    List<ProductResponse> getByCategoryId(UUID categoryId);
    ProductDetail getById(UUID id);
   void deleteById(UUID id);

}
