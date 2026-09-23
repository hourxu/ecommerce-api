package ecommerce.project.respositity;

import ecommerce.project.entity.Category;
import ecommerce.project.entity.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    boolean existsByNameIgnoreCaseAndGender(String name,Gender gender);
    List<Category> findByNameIgnoreCase(String name);
    List<Category>findByGender(Gender gender);
}