package com.enterprise.asset_management.employee.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Builder 
@Data 
public class EmployeeResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long departmentId;
    private String departmentName;
    private Instant createdAt;
}
