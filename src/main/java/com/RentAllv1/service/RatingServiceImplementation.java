package com.RentAllv1.service;

import com.RentAllv1.model.Product;
import com.RentAllv1.model.Rating;
import com.RentAllv1.model.User;
import com.RentAllv1.request.RatingRequest;
import com.RentAllv1.exception.ProductException;
import com.RentAllv1.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImplementation implements RatingService{

    private RatingRepository ratingRepository;
    private ProductService productService;

    public RatingServiceImplementation(RatingRepository ratingRepository,ProductService productService) {
        this.ratingRepository=ratingRepository;
        this.productService=productService;
    }

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {

        Product product=productService.findProductById(req.getProductId());

        Rating rating=new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        // TODO Auto-generated method stub
        return ratingRepository.getAllProductsRating(productId);
    }



}
