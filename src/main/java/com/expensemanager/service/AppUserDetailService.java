package com.expensemanager.service;

import com.expensemanager.entity.ProfileEntity;
import com.expensemanager.repository.ProfileRepo;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@Configuration
public class AppUserDetailService implements UserDetailsService {
    private final ProfileRepo profileRepo;

    public AppUserDetailService(ProfileRepo profileRepo) {
        this.profileRepo = profileRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
  ProfileEntity existing =  profileRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User with email "+email+" not found"));
  return User.builder()
          // username is emial id even while verifying the jwt token
          .username(existing.getEmail())
          .password(existing.getPassword())
          .authorities(Collections.emptyList())
          .build();
    }
}
