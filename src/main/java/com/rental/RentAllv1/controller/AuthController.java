package com.rental.RentAllv1.controller;

import com.rental.RentAllv1.config.JwtTokenProvider;
import com.rental.RentAllv1.exception.UserException;
import com.rental.RentAllv1.model.User;
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
import com.rental.RentAllv1.service.CustomeUserServiceImplementation;

@RestController
@RequestMapping ("/auth")
public class AuthController {

    private UserRepository userRepository;
    private JwtTokenProvider jwtProvider;
    private PasswordEncoder passwordEncoder;

    private CustomeUserServiceImplementation customeUserService;

    public AuthController(UserRepository userRepository, CustomeUserServiceImplementation customeUserService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtProvider) {
        this.userRepository = userRepository;
        this.customeUserService=customeUserService;
        this.passwordEncoder=passwordEncoder;
        this.jwtProvider=jwtProvider;
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
            throw new UserException ("Email already exist with another account-" + email);
        }


        //we create a new user
        User createdUser = new User ();
        createdUser.setEmail (email);
        createdUser.setPassword (passwordEncoder.encode (password));
        createdUser.setFirstName (firstName);
        createdUser.setLastName (lastName);

        //save in database
        User savedUser = userRepository.save (createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken (savedUser.getEmail (), savedUser.getPassword ());
        SecurityContextHolder.getContext ().setAuthentication (authentication);
        String token = jwtProvider.generateToken (authentication);

        AuthResponse authResponse = new AuthResponse ();
        authResponse.setJwt (token);
        authResponse.setMessage ("Signup success");
        return new ResponseEntity<AuthResponse> (authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){

        String username=loginRequest.getEmail ();
        String password=loginRequest.getPassword ();

        Authentication authentication=authenticate(username,password); //validation of user happens here and using
        SecurityContextHolder.getContext ().setAuthentication (authentication);


    SecurityContextHolder.getContext ().setAuthentication (authentication);//sets the authentication object in the security context.
        // This is necessary for the application to know that the user is authenticated.
    String token = jwtProvider.generateToken (authentication);

    AuthResponse authResponse = new AuthResponse();
    authResponse.setJwt (token);
    authResponse.setMessage ("Signin success");

    return new ResponseEntity<AuthResponse> (authResponse, HttpStatus.CREATED);

}

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails= customeUserService.loadUserByUsername (username);
        if(userDetails==null){
            throw new BadCredentialsException ("Invalid Username");
        }
        if(!passwordEncoder.matches (password,userDetails.getPassword ())){
            throw new BadCredentialsException ("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken (userDetails,null,userDetails.getAuthorities ());
    }

}
