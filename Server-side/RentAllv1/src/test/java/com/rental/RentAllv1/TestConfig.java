package com.rental.RentAllv1;


import com.rental.RentAllv1.repository.UserRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public UserRepository userRepository() {
        return new MockUserRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return (PasswordEncoder) new CustomPasswordEncoder();
    }
}
