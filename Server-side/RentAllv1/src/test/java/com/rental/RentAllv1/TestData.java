package com.rental.RentAllv1;

import com.rental.RentAllv1.model.User;
import com.rental.RentAllv1.request.LoginRequest;

public class TestData {

    public static User getTestUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("Welcome");
        user.setFirstName("John");
        user.setLastName("Doe");
        return user;
    }

    public static LoginRequest getLoginRequest() {
        return new LoginRequest("test@example.com", "Welcome");
    }
}
