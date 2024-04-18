package com.rental.RentAllv1.service;

import com.rental.RentAllv1.exception.CartItemException;
import com.rental.RentAllv1.exception.UserException;
import com.rental.RentAllv1.model.Cart;
import com.rental.RentAllv1.model.CartItem;
import com.rental.RentAllv1.model.Product;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId, Long id,CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;

}
