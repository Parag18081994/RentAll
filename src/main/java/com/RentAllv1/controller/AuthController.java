package com.RentAllv1.controller;

import com.RentAllv1.config.JwtTokenProvider;
import com.RentAllv1.exception.UserException;
import com.RentAllv1.model.Cart;
import com.RentAllv1.model.User;
import com.RentAllv1.repository.UserRepository;
import com.RentAllv1.request.LoginRequest;
import com.RentAllv1.response.AuthResponse;
import com.RentAllv1.service.CartService;
import com.RentAllv1.service.CustomUserDetails;
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

@RestController
@RequestMapping ("/auth")
public class AuthController {

    private UserRepository userRepository;
    private JwtTokenProvider jwtProvider;
    private PasswordEncoder passwordEncoder;

    private CustomUserDetails customUserDetails;
    private CartService cartService;

    public AuthController(UserRepository userRepository, CustomUserDetails customUserDetails,CartService cartService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtProvider) {
        this.userRepository = userRepository;
        this.customUserDetails=customUserDetails;
        this.passwordEncoder=passwordEncoder;
        this.jwtProvider=jwtProvider;
        this.cartService=cartService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {

        String email = user.getEmail ();
        String password = user.getPassword ();
        String firstName = user.getFirstName ();
        String lastName = user.getLastName ();

        //check if the user with email already exist
        User isEmailExist = userRepository.findByEmail (email);
        if (isEmailExist != null) {
            throw new UserException ("Email Already Exist With Another Account - " + email);
        }


        //we create a new user
        User createdUser = new User ();
        createdUser.setEmail (email);
        createdUser.setPassword (passwordEncoder.encode (password));
        createdUser.setFirstName (firstName);
        createdUser.setLastName (lastName);

        //saving in database
        User savedUser = userRepository.save (createdUser);
        Cart cart = cartService.createCart(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken (savedUser.getEmail (), savedUser.getPassword ());
        SecurityContextHolder.getContext ().setAuthentication (authentication);
        String token = jwtProvider.generateToken (authentication);

        AuthResponse authResponse = new AuthResponse ();
        authResponse.setJwt (token);
        authResponse.setMessage ("Signup Success");
        return new ResponseEntity<AuthResponse> (authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){

        String username=loginRequest.getEmail ();
        String password=loginRequest.getPassword ();

        Authentication authentication=authenticate(username,password); //validation of user happens here
        SecurityContextHolder.getContext ().setAuthentication (authentication);


    SecurityContextHolder.getContext ().setAuthentication (authentication);
        // Authentication of user is necessary
    String token = jwtProvider.generateToken (authentication);

    AuthResponse authResponse = new AuthResponse();
    authResponse.setJwt (token);
    authResponse.setMessage ("Signin Success");

    return new ResponseEntity<AuthResponse> (authResponse, HttpStatus.CREATED);

}

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException ("Invalid Username !");
        }
        if(!passwordEncoder.matches (password,userDetails.getPassword ())){
            throw new BadCredentialsException ("Invalid Password !");
        }
        return new UsernamePasswordAuthenticationToken (userDetails,null,userDetails.getAuthorities ());
    }

}
