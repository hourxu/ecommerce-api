package ecommerce.project.service.impl;


import ecommerce.project.dto.Cart.AddCartItemRequest;
import ecommerce.project.dto.Cart.CartitemResponse;
import ecommerce.project.dto.Cart.RemoveCartItemRequest;
import ecommerce.project.dto.Cart.ResponseCart;
import ecommerce.project.entity.*;
import ecommerce.project.entity.enums.CartStatus;
import ecommerce.project.exception.CartNotFoundException;
import ecommerce.project.exception.InventoryNotFoundException;
import ecommerce.project.exception.NoActiveCartException;
import ecommerce.project.exception.UserNotFoundException;
import ecommerce.project.mapper.ProductMapper;
import ecommerce.project.respositity.CartItemRepository;
import ecommerce.project.respositity.CartRepository;
import ecommerce.project.respositity.InventoryRepository;
import ecommerce.project.respositity.UserRepository;
import ecommerce.project.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public void addCartItem(AddCartItemRequest request) {
        User user = extractUser();
        //========check cart of user active or not
        Cart cart = cartRepository.findFirstByUser_IdAndStatus(
                        user.getId(),
                        CartStatus.ACTIVE
                )
                .orElseGet(()->createNewCart(user));//solve problem of duplicate user_id
        Inventory inventory = inventoryRepository.findById(request.inventoryId()).orElseThrow(InventoryNotFoundException::new);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setInventory(inventory);
        cartItem.setQuantity(request.quantity());
      cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public void removeCartItem(RemoveCartItemRequest request) {
        User user = extractUser();
        Cart cart = cartRepository
                .findFirstByUser_IdAndStatus(user.getId(), CartStatus.ACTIVE
                )
                .orElseThrow(NoActiveCartException::new);
        Inventory inventory = inventoryRepository.findById(
                request.inventoryId()).orElseThrow(InventoryNotFoundException::new);

        CartItem cartItem = cartItemRepository.findByInventory_Id(
                request.inventoryId()).orElseThrow(CartNotFoundException::new);
        cart.getCartItems().remove(cartItem);
    }

    @Override
    public List<ResponseCart> getall() {
        List<Cart>carts=cartRepository.findAll();
        return carts.stream().map(cart -> new ResponseCart(
                cart.getId(),
                cart.getCartItems()
                        .stream()
                        .map(item->new CartitemResponse(
                                item.getInventory().getId(),
                                item.getInventory().getSize(),
                                item.getQuantity()
                        )).toList()
        )).toList()
                ;    }

//=====who are u who want to add product into cart
    private User extractUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();//check information in spring security,who are you?
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }
//=====check user has already active cart or not
    private Cart createNewCart(User user) {
            Cart  newCart = new Cart();
            newCart.setUser(user);
            newCart.setStatus(CartStatus.ACTIVE);
            return cartRepository.save(newCart);
    }
}
