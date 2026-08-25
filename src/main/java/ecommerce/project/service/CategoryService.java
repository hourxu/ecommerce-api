package ecommerce.project.service;

import ecommerce.project.dto.category.CategoryResponse;
import ecommerce.project.dto.category.CreateCategoryRequest;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponse createCategory(CreateCategoryRequest request);
    List<CategoryResponse>filterCategory(CreateCategoryRequest request);
    List<CategoryResponse> getAllCategory();
    CategoryResponse getCategoryById(UUID id);
    void deletedCategory(UUID id);
}
