package com.enterprise.asset_management.assignment.dto;

import java.time.Instant;

import com.enterprise.asset_management.assignment.model.AssignmentStatus;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class AssignmentResponse {
    private Long assignmentId;
    private Long assetId;
    private String assetName;
    private String serialNumber;
    private Long employeeID;
    private String employeeName;
    private AssignmentStatus status;
    private Instant assignedAt;
    private Instant returnedAt;
    private String notes;
}
