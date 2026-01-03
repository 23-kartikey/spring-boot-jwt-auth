package com.security.test.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.test.DTO.AuthRequest;
import com.security.test.DTO.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService service;

    public AuthController(AuthService service){
        this.service=service;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody AuthRequest request){
        service.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) throws Exception{
        return ResponseEntity.ok(service.login(request));
    }

}
