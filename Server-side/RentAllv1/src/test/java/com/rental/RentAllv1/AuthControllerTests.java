package com.rental.RentAllv1;

import com.rental.RentAllv1.config.JwtTokenProvider;
import com.rental.RentAllv1.controller.AuthController;
import com.rental.RentAllv1.exception.UserException;
import com.rental.RentAllv1.model.User;
import com.rental.RentAllv1.repository.UserRepository;
import com.rental.RentAllv1.request.LoginRequest;
import com.rental.RentAllv1.response.AuthResponse;
import com.rental.RentAllv1.service.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTests {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomUserDetails customUserDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUserHandler() throws UserException {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("Welcome");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setRole("USER");

        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        // Stub the userRepository.save() method with any() matcher for the User object
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<AuthResponse> responseEntity = authController.createUserHandler(user);
        AuthResponse response = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(response);
        assertTrue(response.isSuccess());
    }


    @Test
    public void testCreateUserHandler_EmailAlreadyExists() {
        User existingUser = new User();
        existingUser.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(existingUser);

        User user = new User();
        user.setEmail("test@example.com");

        assertThrows(UserException.class, () -> authController.createUserHandler(user));
    }


    @Test
    void testSignin_Success() {
        // Given
        LoginRequest loginRequest = new LoginRequest("test@example.com", "Welcome");
        when(jwtTokenProvider.generateToken(any())).thenReturn("jwt_token");

        // When
        ResponseEntity<AuthResponse> responseEntity = authController.signin(loginRequest);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        System.out.println (responseEntity.getBody ());
        assertNotNull(responseEntity.getBody());
        assertEquals("jwt_token", responseEntity.getBody().getJwt ());
        assertTrue(responseEntity.getBody().isSuccess());

    }




    @Test
    public void testSignin_InvalidUsername() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        when(customUserDetailsService.loadUserByUsername("test@example.com")).thenReturn(null);

        assertThrows(BadCredentialsException.class, () -> authController.signin(loginRequest));
    }

    @Test
    public void testSignin_InvalidPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("wrong-password"));

        // Mock the loadUserByUsername() method of CustomUserDetails service
        UserDetails userDetails = mock(UserDetails.class);
        when(customUserDetailsService.loadUserByUsername("test@example.com")).thenReturn(userDetails);

        // Throw a BadCredentialsException when password does not match
        when(passwordEncoder.matches("password", userDetails.getPassword())).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> authController.signin(loginRequest));
    }

}
