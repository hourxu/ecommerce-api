package ecommerce.project.service;

import ecommerce.project.dto.AddCartItemRequest;

public interface CartService {

    void addCartItem(AddCartItemRequest request);
}
