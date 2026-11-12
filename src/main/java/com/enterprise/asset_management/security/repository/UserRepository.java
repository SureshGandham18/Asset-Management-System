package com.enterprise.asset_management.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.asset_management.security.model.User;


public interface  UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String email);
}
