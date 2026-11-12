package com.enterprise.asset_management.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class EmployeeCreateRequest {
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank(message = "Eamil is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Department ID is required")
    private Long departmentId;
}
