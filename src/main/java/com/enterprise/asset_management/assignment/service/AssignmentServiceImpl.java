package com.enterprise.asset_management.assignment.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enterprise.asset_management.asset.model.Asset;
import com.enterprise.asset_management.asset.model.AssetStatus;
import com.enterprise.asset_management.asset.repository.AssetRepository;
import com.enterprise.asset_management.assignment.dto.AssignmentResponse;
import com.enterprise.asset_management.assignment.dto.CheckInRequest;
import com.enterprise.asset_management.assignment.dto.CheckOutRequest;
import com.enterprise.asset_management.assignment.model.*;
import com.enterprise.asset_management.assignment.repository.AssetAssignmentRepository;
import com.enterprise.asset_management.common.exception.ResourceNotFoundException;
import com.enterprise.asset_management.employee.model.Employee;
import com.enterprise.asset_management.employee.repository.*;

@Service
@Transactional(readOnly = true)
public class AssignmentServiceImpl implements AssignmentService {
    private final AssetRepository assetRepository;
    private final EmployeeRepository employeeRepository;
    private final AssetAssignmentRepository assetAssignmentRepository;

    public AssignmentServiceImpl(AssetRepository assetRepository, EmployeeRepository employeeRepository,
            AssetAssignmentRepository assetAssignmentRepository) {
        this.assetRepository = assetRepository;
        this.employeeRepository = employeeRepository;
        this.assetAssignmentRepository = assetAssignmentRepository;
    }

    @Transactional
    public AssignmentResponse checkOutAsset(CheckOutRequest request) {
        Asset asset = assetRepository.findById(request.getAssetId())
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID: " + request.getAssetId()));

        if (asset.getStatus() != AssetStatus.AVAILABLE) {
            throw new IllegalStateException("Asset cannot be checked out. Current status: " + asset.getStatus());
        }

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Employee not found with ID: " + request.getEmployeeId()));

        asset.setStatus(AssetStatus.ASSIGNED);
        assetRepository.save(asset);

        AssetAssignment assignment = AssetAssignment.builder()
                .asset(asset)
                .employee(employee)
                .assignedAt(Instant.now())
                .status(AssignmentStatus.ACTIVE)
                .notes(request.getNotes())
                .build();
        AssetAssignment savedAssignment = assetAssignmentRepository.save(assignment);
        return mapToResponse(savedAssignment);
    }

    @Transactional 
    public AssignmentResponse checkInAsset(Long assetId, CheckInRequest request){
        // checking asset existence
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID: "+assetId));
        // checking whether the asset in Assigned state or not
        if(asset.getStatus() != AssetStatus.ASSIGNED){
            throw new IllegalStateException("Asset cannot be returned. Current status: "+ asset.getStatus());
        }

        // locate the active assignment record
        AssetAssignment assignment = assetAssignmentRepository.findByAssetIdAndStatus(assetId, AssignmentStatus.ACTIVE)
                        .orElseThrow(()-> new ResourceNotFoundException("No active assignment found for Asset ID: "+ assetId));
        
        // change assignment status to returned and update the notes
        assignment.setStatus(AssignmentStatus.RETURNED);
        assignment.setReturnedAt(Instant.now());
        if(request != null && request.getNotes() != null){
            assignment.setNotes(request.getNotes());
        }
        assetAssignmentRepository.save(assignment);

        // update the asset availability
        asset.setStatus(AssetStatus.AVAILABLE);
        assetRepository.save(asset);

        return mapToResponse(assignment);
    }

    public List<AssignmentResponse> getAssignmentHistoryByAsset(Long assetId){
        if(!assetRepository.existsById(assetId)){
            throw new ResourceNotFoundException("Asset not found wit ID: "+assetId);
        }
        return assetAssignmentRepository.findByAssetIdOrderByAssignedAtDesc(assetId).stream()
                    .map(this::mapToResponse).toList();
    }

    public AssignmentResponse mapToResponse(AssetAssignment assetAssignment){
        return AssignmentResponse.builder().
                assignmentId(assetAssignment.getId())
                .assetId(assetAssignment.getAsset().getId())
                .assetName(assetAssignment.getAsset().getName())
                .serialNumber(assetAssignment.getAsset().getSerialNumber())
                .employeeID(assetAssignment.getEmployee().getId())
                .employeeName(assetAssignment.getEmployee().getFirstName() + " " + assetAssignment.getEmployee().getLastName())
                .status(assetAssignment.getStatus())
                .assignedAt(assetAssignment.getAssignedAt())
                .returnedAt(assetAssignment.getReturnedAt())
                .notes(assetAssignment.getNotes())
                .build();
    }

}
