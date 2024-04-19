package com.RentAllv1.service;

import com.RentAllv1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.RentAllv1.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetails implements UserDetailsService {

    private UserRepository userRepository;


    public CustomUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepository.findByEmail (username);
        if(user==null){
            throw new UsernameNotFoundException ("username not found by email-"+username);
        }
        List<GrantedAuthority> authorities=new ArrayList<> ();
        return new org.springframework.security.core.userdetails.User (user.getEmail (),user.getPassword (),authorities);
    }
}
