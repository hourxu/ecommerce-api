package ecommerce.project.respositity;

import ecommerce.project.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    //=====The value might exist, or it might not exist.
    Optional<CartItem> findByInventory_Id(UUID inventoryId);
}
