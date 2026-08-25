package ecommerce.project.service;

import ecommerce.project.dto.product.ProductDetail;
import ecommerce.project.dto.product.ProductRequest;
import ecommerce.project.dto.product.ProductResponse;
import ecommerce.project.entity.Product;
import ecommerce.project.respositity.ProductRepository;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(UUID id, ProductRequest request);
    List<ProductResponse>getAll();
    List<ProductResponse>getid(UUID categoryId);
    ProductDetail productDetail(UUID id);
   ProductResponse deletedId(UUID id);

}
