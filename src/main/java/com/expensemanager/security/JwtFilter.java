package com.expensemanager.security;

import com.expensemanager.util.Jwtutil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final Jwtutil jwtUtil;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // validate the token
        // get the header from request

        final String auth =  request.getHeader("Authorization");

        if(StringUtils.hasText(auth) && auth.startsWith("Bearer ")){
            String token = auth.substring(7);
            String email = jwtUtil.extractUsername(token);
            // security cotntext will be null intially
            if(email!= null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails user = userDetailsService.loadUserByUsername(email);
                if(jwtUtil.isValidToken(token, user)){
                    UsernamePasswordAuthenticationToken authtok = new UsernamePasswordAuthenticationToken(user , null,user.getAuthorities());
                    authtok.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authtok);


                }

            }

        }
        filterChain.doFilter(request,response);

    }
}