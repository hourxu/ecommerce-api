package ecommerce.project.service;

import ecommerce.project.dto.AddCartItemRequest;
import ecommerce.project.dto.RemoveCartItemRequest;

import java.util.UUID;

public interface CartService {

    void addCartItem(AddCartItemRequest request);
    void removeCartItem(RemoveCartItemRequest request);
}
