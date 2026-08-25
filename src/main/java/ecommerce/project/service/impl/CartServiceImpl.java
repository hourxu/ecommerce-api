package ecommerce.project.service.impl;


import ecommerce.project.dto.AddCartItemRequest;
import ecommerce.project.entity.User;
import ecommerce.project.respositity.CartItemRepository;
import ecommerce.project.respositity.CartRepository;
import ecommerce.project.respositity.InventoryRepository;
import ecommerce.project.respositity.UserRepository;
import ecommerce.project.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final InventoryRepository inventoryRepository;
    @Override
    public void addCartItem(AddCartItemRequest request) {
        User user = extractUser();
    }

    private User extractUser() {
       OAuth2ResourceServerProperties.Jwt jwt = (OAuth2ResourceServerProperties.Jwt) SecurityContextHolder.getContext().getAuthentication();
      return null;
    }
}
