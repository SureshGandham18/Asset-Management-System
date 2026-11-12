package com.enterprise.asset_management.security.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.enterprise.asset_management.security.model.User;
import com.enterprise.asset_management.security.repository.UserRepository;

@Service 
public class CustomUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;
    public  CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.isEnabled(),
            true, true, true,
            Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}
