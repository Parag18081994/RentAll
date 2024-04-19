package com.RentAllv1.service;

import com.RentAllv1.model.Review;
import com.RentAllv1.model.User;
import com.RentAllv1.request.ReviewRequest;
import com.RentAllv1.exception.ProductException;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest req, User user) throws ProductException;

    public List<Review> getAllReview(Long productId);


}
