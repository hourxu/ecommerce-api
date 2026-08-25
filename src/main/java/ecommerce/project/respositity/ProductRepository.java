package ecommerce.project.respositity;

import ecommerce.project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByCategoryId(UUID id);
    List<Product> findByCategoryId(UUID categoryId);
}
