package com.rental.RentAllv1.controller;

import com.rental.RentAllv1.config.JwtTokenProvider;
import com.rental.RentAllv1.exception.UserException;
import com.rental.RentAllv1.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rental.RentAllv1.repository.UserRepository;
import com.rental.RentAllv1.request.LoginRequest;
import com.rental.RentAllv1.response.AuthResponse;
import com.rental.RentAllv1.service.CustomUserDetails;

@RestController
@RequestMapping ("/auth")
public class AuthController {

    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;

    private CustomUserDetails customUserDetails;

    public AuthController(UserRepository userRepository, CustomUserDetails customeUserService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtProvider) {
        this.userRepository = userRepository;
        this.customUserDetails = customeUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider=jwtProvider;
    }

    @PostMapping ("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody User user) throws UserException {

        String email = user.getEmail ();
        String password = user.getPassword ();
        String firstName = user.getFirstName ();
        String lastName = user.getLastName ();
        String role = user.getRole ();

        //check if the user with email already exist
        User isEmailExist = userRepository.findByEmail (email);

        if (isEmailExist != null) {
            throw new UserException ("Email already exist with another account-" + email);
        }


        //we create a new user
        User createdUser = new User ();
        createdUser.setEmail (email);
        createdUser.setPassword (passwordEncoder.encode (password));
        createdUser.setFirstName (firstName);
        createdUser.setLastName (lastName);
        createdUser.setRole (role);

        //save in database
        User savedUser = userRepository.save (createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken (email, password);
        SecurityContextHolder.getContext ().setAuthentication (authentication);
        String token = jwtTokenProvider.generateToken (authentication);

        AuthResponse authResponse = new AuthResponse (token, true);

        System.out.println (authResponse+" Token");
        return new ResponseEntity<AuthResponse> (authResponse, HttpStatus.OK);
    }

    @PostMapping ("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {

        String username = loginRequest.getEmail ();
        String password = loginRequest.getPassword ();
        System.out.println(username +" ----- "+password);

        Authentication authentication = authenticate (username, password); //validation of user happens here and using
        SecurityContextHolder.getContext ().setAuthentication (authentication);//sets the authentication object in the security context.
        // This is necessary for the application to know that the user is authenticated.

        String token = jwtTokenProvider.generateToken (authentication);

        System.out.println ("JWT Token "+ token);

        AuthResponse authResponse = new AuthResponse (token,true);
       /* authResponse.setJwt (token);
        authResponse.setMessage ("Signin success");*/

        return new ResponseEntity<AuthResponse> (authResponse, HttpStatus.OK);

    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customUserDetails.loadUserByUsername (username);

        System.out.println ("sign in userDetails - " + userDetails);

        if (userDetails == null) {
            System.out.println ("sign in userDetails - null " + userDetails);
            throw new BadCredentialsException ("Invalid username or password");
        }
        if (!passwordEncoder.matches (password, userDetails.getPassword ())) {
            System.out.println ("sign in userDetails - password not match " + userDetails);
            throw new BadCredentialsException ("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken (userDetails, null, userDetails.getAuthorities ());
    }
}
