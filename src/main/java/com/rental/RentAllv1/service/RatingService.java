package com.rental.RentAllv1.service;

import com.rental.RentAllv1.exception.ProductException;
import com.rental.RentAllv1.model.Rating;
import com.rental.RentAllv1.model.User;
import com.rental.RentAllv1.request.RatingRequest;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingRequest req, User user) throws ProductException;

    public List<Rating> getProductsRating(Long productId);

}
