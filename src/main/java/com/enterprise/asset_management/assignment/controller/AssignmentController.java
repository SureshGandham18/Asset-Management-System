package com.enterprise.asset_management.assignment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.asset_management.assignment.dto.AssignmentResponse;
import com.enterprise.asset_management.assignment.dto.CheckInRequest;
import com.enterprise.asset_management.assignment.dto.CheckOutRequest;
import com.enterprise.asset_management.assignment.service.AssignmentService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/assignments") 
public class AssignmentController {
    private final AssignmentService assignmentService;
    public AssignmentController(AssignmentService assignmentService){
        this.assignmentService = assignmentService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<AssignmentResponse> checkout(@Valid @RequestBody CheckOutRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(assignmentService.checkOutAsset(request));
    } 

    @PostMapping("/checkin/{assetId}")
    public ResponseEntity<AssignmentResponse> checkin(@PathVariable Long assetId,
        @RequestBody(required = false) CheckInRequest request){
            return ResponseEntity.ok(assignmentService.checkInAsset(assetId, request));
    }

    @GetMapping("/history/asset/{assetId}")
    public ResponseEntity<List<AssignmentResponse>> getHistory(@PathVariable Long assetId){
        return ResponseEntity.ok(assignmentService.getAssignmentHistoryByAsset(assetId));
    }
    
}
