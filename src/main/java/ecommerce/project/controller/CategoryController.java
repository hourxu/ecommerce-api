package ecommerce.project.controller;

import ecommerce.project.dto.category.CategoryResponse;
import ecommerce.project.dto.category.CreateCategoryRequest;
import ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestBody @Valid CreateCategoryRequest request
            ){

        return  new ResponseEntity<>(
                categoryService.createCategory(request),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/filter")
    public ResponseEntity<List<CategoryResponse>>filterCategory(
            @RequestBody @Valid CreateCategoryRequest request)
    {
        return new ResponseEntity<>(
                categoryService.filterCategory(request),
        HttpStatus.OK
                );
    }
    @GetMapping
    public ResponseEntity<List<CategoryResponse>>getCategory(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse>getByid(@PathVariable UUID id){

        return new ResponseEntity<>(
                categoryService.getCategoryById(id),
                HttpStatus.OK
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?>deleted(@PathVariable UUID id){
        categoryService.deletedCategory(id);
        return ResponseEntity.ok().build();
    }
}