package ecommerce.project.respositity;

import ecommerce.project.entity.Cart;
import ecommerce.project.entity.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findFirstByUser_IdAndStatus(UUID userId, CartStatus status);
}

