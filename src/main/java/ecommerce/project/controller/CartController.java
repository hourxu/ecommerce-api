package ecommerce.project.controller;

import ecommerce.project.dto.Cart.AddCartItemRequest;
import ecommerce.project.dto.Cart.RemoveCartItemRequest;
import ecommerce.project.dto.Cart.ResponseCart;
import ecommerce.project.service.impl.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@RestController
public class CartController {
    private final CartServiceImpl cartService;

    @PostMapping
    public ResponseEntity<ResponseCart> addToCart(@RequestBody AddCartItemRequest request){
        cartService.addCartItem(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping
    public ResponseEntity<Void>removeCartItem(@RequestBody RemoveCartItemRequest request){
        cartService.removeCartItem(request);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<?>>getAll(){
     return new ResponseEntity<>(
             cartService.getall(),
             HttpStatus.OK
     );
    }
}
