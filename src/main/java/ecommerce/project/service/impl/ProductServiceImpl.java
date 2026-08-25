package ecommerce.project.service.impl;

import ecommerce.project.dto.inventory.InventoryResponse;
import ecommerce.project.dto.product.ProductDetail;
import ecommerce.project.dto.product.ProductRequest;
import ecommerce.project.dto.product.ProductResponse;
import ecommerce.project.entity.Category;
import ecommerce.project.entity.Inventory;
import ecommerce.project.entity.Product;
import ecommerce.project.exception.CategoryNotFoundException;
import ecommerce.project.exception.DeleteSuccessException;
import ecommerce.project.exception.ProductAlreadyExistedException;
import ecommerce.project.exception.ProductNotFoundException;
import ecommerce.project.mapper.InventoryMapper;
import ecommerce.project.mapper.ProductMapper;
import ecommerce.project.respositity.CategoryRepository;
import ecommerce.project.respositity.ProductRepository;
import ecommerce.project.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final InventoryMapper inventoryMapper;
    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {

        //::::::: validate category
        Category category = categoryRepository.findById(
                request.categoryId()).orElseThrow(CategoryNotFoundException::new);

        //:::::: validate uniqueness of product name
        boolean existed=productRepository.existsByNameIgnoreCase(request.name());
        if(existed){
            throw new ProductAlreadyExistedException();
        }

        Product product =productMapper.toProduct(request,category);
        Product save=productRepository.save(product);
        return productMapper.toProductResponse(save);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID id,ProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(
                ProductNotFoundException::new
        );
        boolean nameExisted=productRepository.existsByNameIgnoreCase(request.name());
        if(nameExisted
                && !product.getName().equals(request.name())
        ){
            throw new ProductAlreadyExistedException();
        }
        product.setName(request.name());
        product.setPrice(request.price());
        product.setDescription(request.description());
        return productMapper.toProductResponse(product);
    }

    @Override
    //filter product
    public List<ProductResponse> getid(UUID categoryId) {
        List<Product> products ;
        //check id of category
        if (categoryId != null){
            products = productRepository.findByCategoryId(categoryId);
        }else {
            products = productRepository.findAll();
        }
        //if know id of category show data of product
        return products.stream()
                .map(productMapper::toProductResponse)
                .toList();

    }
    @Override
    //show product all
    public List<ProductResponse> getAll() {
        List<Product> products =productRepository.findAll();
        return products.stream()
                .map(productMapper::toProductResponse)
                .toList();

    }
    @Override
    //show product of inventory
    public ProductDetail productDetail(UUID id) {
       Product product= productRepository.findById(id)
               .orElseThrow(ProductNotFoundException::new);
       List<InventoryResponse>inventoryResponses=product// check id of product null show nothing , but product has id show data from inventory
             //check condition
               .getInventory()==null ? List.of():
               product.getInventory().stream()
                       .map(inventoryMapper::toInventoryResponse)
                       .toList();
        return new ProductDetail(inventoryResponses);
    }

    @Override
    public ProductResponse deletedId(UUID id) {

        Product product=productRepository.findById(id).
                orElseThrow(ProductNotFoundException::new);
        productRepository.deleteById(id);
        throw new DeleteSuccessException();
    }

}
