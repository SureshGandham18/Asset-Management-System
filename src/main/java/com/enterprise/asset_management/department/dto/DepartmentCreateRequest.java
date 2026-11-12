package com.enterprise.asset_management.department.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class DepartmentCreateRequest {
    @NotBlank(message = "Department name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Department code is required")
    @Size(min = 2, max = 20, message = "Code must")
    private String code;
}
