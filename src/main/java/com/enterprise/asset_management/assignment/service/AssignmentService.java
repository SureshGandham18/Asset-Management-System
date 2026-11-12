package com.enterprise.asset_management.assignment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.enterprise.asset_management.assignment.dto.AssignmentResponse;
import com.enterprise.asset_management.assignment.dto.CheckInRequest;
import com.enterprise.asset_management.assignment.dto.CheckOutRequest;

@Service 
public interface AssignmentService {
    AssignmentResponse checkOutAsset(CheckOutRequest request);
    AssignmentResponse checkInAsset(Long assetId, CheckInRequest request);
    List<AssignmentResponse> getAssignmentHistoryByAsset(Long assetId);
}
