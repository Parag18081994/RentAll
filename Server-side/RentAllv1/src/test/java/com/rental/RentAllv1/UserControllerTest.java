package com.rental.RentAllv1;

import com.rental.RentAllv1.controller.UserController;
import com.rental.RentAllv1.exception.UserException;
import com.rental.RentAllv1.model.User;
import com.rental.RentAllv1.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.ACCEPTED;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    // Test for getting user profile with a valid JWT
    @Test
    void testGetUserProfileValidJwt() throws UserException {
        // Mocked data
        String validJwt = "validJwt";
        User mockUser = new User(); // Set up the mocked user details
        mockUser.setUsername("john_doe");

        // Stubbing
        when(userService.findUserProfileByJwt(validJwt)).thenReturn(mockUser);

        // Call the endpoint
        ResponseEntity<User> response = userController.getUserProfileHandler(validJwt);

        // Verify response
        assertEquals(ACCEPTED, response.getStatusCode());
        assertEquals("john_doe", response.getBody().getUsername());

        // Verify interactions
        verify(userService, times(1)).findUserProfileByJwt(validJwt);
    }

    // Test for getting user profile with an invalid JWT
    @Test
    void testGetUserProfileInvalidJwt() throws UserException {
        // Mocked data
        String invalidJwt = "invalidJwt";

        // Stubbing
        when(userService.findUserProfileByJwt(invalidJwt)).thenThrow(new UserException("Invalid JWT"));

        // Assert exception
        assertThrows(UserException.class, () -> userController.getUserProfileHandler(invalidJwt));

        // Verify interactions
        verify(userService, times(1)).findUserProfileByJwt(invalidJwt);
    }

    // Test for getting all users
    @Test
    void testGetAllUsers() throws UserException {
        // Mocked data
        List<User> mockUserList = new ArrayList<>();
        User user1 = new User();
        user1.setUsername("user1");
        mockUserList.add(user1);

        User user2 = new User();
        user2.setUsername("user2");
        mockUserList.add(user2);

        // Stubbing
        when(userService.findAllUsers()).thenReturn(mockUserList);

        // Call the endpoint
        ResponseEntity<List<User>> response = userController.getAllUsers("validJwt");

        // Verify response
        assertEquals(ACCEPTED, response.getStatusCode());
        assertEquals(2, response.getBody().size());

        // Verify interactions
        verify(userService, times(1)).findAllUsers();
    }
}
