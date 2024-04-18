package com.rental.RentAllv1.service;

import com.rental.RentAllv1.exception.ProductException;
import com.rental.RentAllv1.model.Cart;
import com.rental.RentAllv1.model.User;
import com.rental.RentAllv1.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);
}
