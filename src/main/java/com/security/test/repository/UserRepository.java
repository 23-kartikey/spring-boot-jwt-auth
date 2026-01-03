package com.security.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.test.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    public User findByEmail(String email);
    public Boolean existsByEmail(String email);
}
