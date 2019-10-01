package com.imall.cart.web;

import com.imall.cart.service.CartService;
import com.imall.cart.pojo.Cart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        cartService.addCart(cart);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<Cart>> getCarts(){
        List<Cart> carts = cartService.getCarts();
        if (CollectionUtils.isEmpty(carts)) {
            return null;
        }
        return ResponseEntity.ok(carts);
    }


}
