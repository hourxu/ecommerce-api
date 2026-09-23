package ecommerce.project.service;

import ecommerce.project.dto.category.CategoryResponse;
import ecommerce.project.dto.category.CreateCategoryRequest;
import ecommerce.project.dto.category.RequestGender;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponse createCategory( CreateCategoryRequest request) throws IOException;
    List<CategoryResponse>filterCategory(CreateCategoryRequest request);
    List<CategoryResponse> getAllCategory();
    List<CategoryResponse>findGender(RequestGender requestGender);
    CategoryResponse getCategoryById(UUID id);
    void deletedCategory(UUID id);
}
