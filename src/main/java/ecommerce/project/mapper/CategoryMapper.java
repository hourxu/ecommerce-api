package ecommerce.project.mapper;

import ecommerce.project.dto.category.CategoryResponse;
import ecommerce.project.dto.category.CreateCategoryRequest;
import ecommerce.project.entity.Category;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    @Value("${cloudflare.r2.public-url}")
    private String r2PublicUrl;

    public Category toCategory(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        category.setGender(request.gender());
        category.setDescription(request.description());
        return category;
    }

    public CategoryResponse toCategoryResponse(Category category) {
        String imageUrl = null;
        if (category.getImage() != null) {
            imageUrl = buildImageUrl(category.getImage().getImageUrl());
        }
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getGender(),
                imageUrl,
                category.getDescription()
        );
    }

    private String buildImageUrl(String key) {
        if (key == null || key.isEmpty()) return null;
        return r2PublicUrl + "/" + key;
    }
}