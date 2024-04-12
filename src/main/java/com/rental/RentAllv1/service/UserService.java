package com.rental.RentAllv1.service;



import com.rental.RentAllv1.exception.UserException;
import com.rental.RentAllv1.model.User;

public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

}
