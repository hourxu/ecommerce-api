package ecommerce.project.service.impl;

import ecommerce.project.dto.ImageResponse;
import ecommerce.project.dto.category.CategoryResponse;
import ecommerce.project.dto.category.CreateCategoryRequest;
import ecommerce.project.dto.category.RequestGender;
import ecommerce.project.entity.Category;
import ecommerce.project.entity.Product;
import ecommerce.project.exception.CategoryAlreadyExistedException;
import ecommerce.project.exception.CategoryHasProductsException;
import ecommerce.project.exception.CategoryNotFoundException;
import ecommerce.project.mapper.CategoryMapper;
import ecommerce.project.respositity.CategoryRepository;
import ecommerce.project.respositity.ProductRepository;
import ecommerce.project.service.CategoryService;
import ecommerce.project.service.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ImageService imageService;
    @Override
    public CategoryResponse createCategory( CreateCategoryRequest request){
        // :::::::::::::::::::: map from request to entity
        Category category = categoryMapper.toCategory(request);

        // :::::::::::::::::::: validate uniqueness of category name
        boolean existed = categoryRepository.existsByNameIgnoreCaseAndGender(
                category.getName(),
                category.getGender()
        );

        if(existed){
            throw new CategoryAlreadyExistedException();
        }
        // :::::::::::::::::::::: save to database
        Category savedCategory = categoryRepository.save(category);


        Category getURl=categoryRepository.findById(savedCategory.getId()).orElseThrow(CategoryNotFoundException::new);
        return categoryMapper.toCategoryResponse(getURl);
    }



    @Override
    public List<CategoryResponse> filterCategory(CreateCategoryRequest request) {
        List<Category> categories=List.of();
        //:::check name in list
        if(request.name()!=null){
            categories=categoryRepository.findByNameIgnoreCase(request.name());
        }
        if(categories.isEmpty()){
            throw new CategoryNotFoundException();
        }
        //:::response category that has in list
        return categories.stream().map(categoryMapper::toCategoryResponse).toList();
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        List<Category>categories=categoryRepository.findAll();
        return categories.stream().map(categoryMapper::toCategoryResponse).toList();
    }

    @Override
    public List<CategoryResponse> findGender(RequestGender requestGender) {
            List<Category>categories= categoryRepository.findByGender(requestGender.gender());
            if(categories.isEmpty()){
                throw new CategoryNotFoundException();
            }
            return categories.stream().map(categoryMapper::toCategoryResponse).toList();
    }

    @Override
    public CategoryResponse getCategoryById(UUID id) {
        Category category=categoryRepository.findById(id).orElseThrow(
                CategoryNotFoundException::new);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    @Transactional
    public void deletedCategory(UUID id) {

        categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        categoryRepository.deleteById(id);
    }


}
