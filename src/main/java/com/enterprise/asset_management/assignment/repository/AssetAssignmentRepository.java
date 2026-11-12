package com.enterprise.asset_management.assignment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.asset_management.assignment.model.AssetAssignment;
import com.enterprise.asset_management.assignment.model.AssignmentStatus;
public interface AssetAssignmentRepository extends JpaRepository<AssetAssignment, Long>{
    @EntityGraph(attributePaths = {"asset", "employee"})
    Optional<AssetAssignment> findByAssetIdAndStatus(Long assetId, AssignmentStatus status);
    
    @EntityGraph(attributePaths = {"asset", "employee"})
    List<AssetAssignment> findByAssetIdOrderByAssignedAtDesc(Long assetId);

    @EntityGraph(attributePaths = {"asset", "employee"})
    List<AssetAssignment> findByEmployeeIdOrderByAssignedAtDesc(Long employeeId);
}