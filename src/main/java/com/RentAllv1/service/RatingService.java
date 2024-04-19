package com.RentAllv1.service;

import com.RentAllv1.exception.ProductException;
import com.RentAllv1.model.Rating;
import com.RentAllv1.model.User;
import com.RentAllv1.request.RatingRequest;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingRequest req, User user) throws ProductException;

    public List<Rating> getProductsRating(Long productId);

}
