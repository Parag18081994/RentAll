package com.RentAllv1.service;

import com.RentAllv1.model.Cart;
import com.RentAllv1.model.User;
import com.RentAllv1.request.AddItemRequest;
import com.RentAllv1.exception.ProductException;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);
}
