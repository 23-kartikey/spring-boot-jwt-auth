package com.security.test.entity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.security.test.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    
    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo){
        this.userRepo=userRepo;
    }

    public User loadUserByUsername(String email){
        return userRepo.findByEmail(email);
    }

}
