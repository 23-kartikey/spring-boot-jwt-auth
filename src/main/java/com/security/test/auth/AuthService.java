package com.security.test.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.test.DTO.AuthRequest;
import com.security.test.DTO.AuthResponse;
import com.security.test.entity.User;
import com.security.test.jwt.JwtService;
import com.security.test.repository.UserRepository;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;

    public AuthService(UserRepository userRepo, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder){
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
        this.passwordEncoder=passwordEncoder;
        this.userRepo=userRepo;
    }

    public void register(AuthRequest request){
        if(userRepo.existsByEmail(request.email())){
            throw new IllegalStateException("Email already registered");
        }
        User user=new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepo.save(user);
    }

    public AuthResponse login(AuthRequest request){
       Authentication authentication= authenticationManager
                    .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        request.email(), request.password()
                    )
                );
        UserDetails user=(UserDetails)authentication.getPrincipal();
        String token=jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}
