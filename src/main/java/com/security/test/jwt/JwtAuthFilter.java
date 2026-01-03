package com.security.test.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserDetailsService userService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userService){
        this.jwtService=jwtService;
        this.userService=userService;
    }

    public void  doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
        ) throws IOException, ServletException{
            
            String auth=request.getHeader("Authentication");

            if(auth==null||!auth.startsWith("Bearer ")){
                filterChain.doFilter(request, response);
                return;
            }

            String token=auth.substring(7);
            String email=jwtService.extractEmail(token);

            if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails user=userService.loadUserByUsername(email);

                if(jwtService.isTokenValid(token)){
                    UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(
                        user.getUsername(), null, user.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                filterChain.doFilter(request,response);
            }
    }
}