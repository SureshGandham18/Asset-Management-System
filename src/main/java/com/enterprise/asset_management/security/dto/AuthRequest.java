package com.enterprise.asset_management.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data 
public class AuthRequest {
    @NotBlank 
    private String username;
    @NotBlank 
    private String password;
}
