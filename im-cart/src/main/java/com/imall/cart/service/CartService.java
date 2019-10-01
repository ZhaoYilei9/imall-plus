package com.imall.cart.service;

import com.imall.cart.pojo.Cart;

import java.util.List;

public interface CartService {

    void addCart(Cart cart);

    List<Cart> getCarts();
}
