package com.security.test.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User implements UserDetails{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    private boolean enabled=true;

    public long getId(){
        return id;
    }

    public String getEmail(){
        return email;
    }

    @Override
    public String getPassword(){
        return password;
    }

    public void setId(long id){
        this.id=id;
    }

    public void setEmail(String email){
        this.email=email;
    }


    public void setPassword(String password){
        this.password=password;
    }

    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of();
    }

    @Override
    public boolean isEnabled(){return enabled;}

    @Override 
    public boolean isAccountNonExpired(){return true;}

    @Override
    public boolean isCredentialsNonExpired(){return true;}

    @Override
    public boolean isAccountNonLocked(){return true;}
}
