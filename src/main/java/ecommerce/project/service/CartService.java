package ecommerce.project.service;

import ecommerce.project.dto.Cart.AddCartItemRequest;
import ecommerce.project.dto.Cart.RemoveCartItemRequest;
import ecommerce.project.dto.Cart.ResponseCart;

import java.util.List;
import java.util.UUID;

public interface CartService {

    void addCartItem(AddCartItemRequest request);
    void removeCartItem(RemoveCartItemRequest request);
    List<ResponseCart>getall();
}
