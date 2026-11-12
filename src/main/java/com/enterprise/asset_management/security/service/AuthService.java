package com.enterprise.asset_management.security.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enterprise.asset_management.security.repository.UserRepository;
import com.enterprise.asset_management.common.exception.DuplicateResourceException;
import com.enterprise.asset_management.security.dto.AuthRequest;
import com.enterprise.asset_management.security.dto.AuthResponse;
import com.enterprise.asset_management.security.dto.RegisterRequest;
import com.enterprise.asset_management.security.jwt.JwtService;
import com.enterprise.asset_management.security.model.User;

// import jakarta.websocket.server.ServerEndpoint;

@Service 
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
        JwtService jwtService, UserDetailsService userDetailsService,
        AuthenticationManager authenticationManager
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional 
    public AuthResponse register(RegisterRequest request){
        if(userRepository.existsByUsernameIgnoreCase(request.getUsername())){
            throw new DuplicateResourceException("Username '"+request.getUsername()+"' is already taken.");
        }
        if(userRepository.existsByEmailIgnoreCase(request.getEmail())){
            throw new DuplicateResourceException("Email '"+request.getEmail()+"' is already taken.");
        }

        User user = User.builder()
                .username(request.getUsername().trim())
                .email(request.getEmail().trim().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .enabled(true)
                .build();

        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String jwtToken = jwtService.generateToken(userDetails);
        return AuthResponse.builder()
                    .token(jwtToken)
                    .username(user.getUsername())
                    .role(user.getRole().name())
                    .build();
    }

    public AuthResponse login(AuthRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String jwtToken = jwtService.generateToken(userDetails);

        String userRole = userDetails.getAuthorities().stream()
                    .findFirst()
                    .map(Object::toString)
                    .orElse("ROLE_EMPLOYEE");

        return AuthResponse.builder()
                    .token(jwtToken)
                    .username(userDetails.getUsername())
                    .role(userRole)
                    .build();
    }

    
    
}
