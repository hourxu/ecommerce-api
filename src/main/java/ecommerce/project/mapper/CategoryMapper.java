package ecommerce.project.mapper;

import ecommerce.project.dto.category.CategoryResponse;
import ecommerce.project.dto.category.CreateCategoryRequest;
import ecommerce.project.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

   public Category toCategory(CreateCategoryRequest request){
        Category category = new Category();
        category.setName(request.name());
        category.setDescription(request.description());
        return category;
    }
   public CategoryResponse toCategoryResponse(Category category){
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
