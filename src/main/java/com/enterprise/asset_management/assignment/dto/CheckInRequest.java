package com.enterprise.asset_management.assignment.dto;

// import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class CheckInRequest {
    @Size(max = 255, message = "Notes cannot exceed 255 characters")
    private  String notes;
}
