package com.rental.RentAllv1.service;

import com.rental.RentAllv1.exception.ProductException;
import com.rental.RentAllv1.model.Review;
import com.rental.RentAllv1.model.User;
import com.rental.RentAllv1.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest req, User user) throws ProductException;

    public List<Review> getAllReview(Long productId);


}
