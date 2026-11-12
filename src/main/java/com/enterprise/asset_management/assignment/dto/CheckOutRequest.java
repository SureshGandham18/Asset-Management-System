package com.enterprise.asset_management.assignment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class CheckOutRequest {
    @NotNull(message = "Asset ID is required")
    private Long assetId;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @Size(max = 255, message = "Notes cannot exceed 255 characters")
    private String notes;
}
