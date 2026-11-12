package com.enterprise.asset_management.department.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enterprise.asset_management.asset.dto.AssetResponse;
import com.enterprise.asset_management.common.exception.DuplicateResourceException;
import com.enterprise.asset_management.common.exception.ResourceNotFoundException;
import com.enterprise.asset_management.department.dto.DepartmentCreateRequest;
import com.enterprise.asset_management.department.dto.DepartmentResponse;
import com.enterprise.asset_management.department.model.Department;
import com.enterprise.asset_management.department.repository.DepartmentRepository;

@Service 
@Transactional(readOnly = true) 
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    public DepartmentServiceImpl(DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }

    @Transactional 
    public DepartmentResponse createDepartment(DepartmentCreateRequest request){
        if(departmentRepository.existsByCodeIgnoreCase(request.getCode().trim())){
            throw new DuplicateResourceException("Department with code '"+ request.getCode() + "' already exists.");
        }

        Department department = Department.builder()
            .name(request.getName().trim())
            .code(request.getCode().toUpperCase().trim())
            .build();
        Department saved = departmentRepository.save(department);
        return mapToResponse(saved);
    }

    public List<DepartmentResponse> getAllDepartmentsWithAssets(){
        List<Department> departments = departmentRepository.findAllWithAssetsJoinFetch();
        return departments.stream().map(this::mapToResponse).toList();
    }

    public DepartmentResponse getDepartmentById(Long id){
        Department department = departmentRepository.findByIdWithAssetsGraph(id).
            orElseThrow(() -> new ResourceNotFoundException("Department not found witj ID: "+ id));
        return mapToResponse(department);
    }

    @Transactional 
    public void deleteDepartment(Long id){
        Department department = departmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: "+id));

        if(!department.getAssets().isEmpty()){
            throw new IllegalStateException("Cannot delete department with actove assets assigned to it.");
        }

        departmentRepository.delete(department);
    }


    private DepartmentResponse mapToResponse(Department department){
        List<AssetResponse> assetResponses = department.getAssets().stream()
                .map(asset -> AssetResponse.builder()
                .id(asset.getId())
                .name(asset.getName())
                .serialNumber(asset.getSerialNumber())
                .assetType(asset.getAssetType())
                .status(asset.getStatus())
                .createdAt(asset.getCreatedAt())
                .build()
            ).toList();

        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .code(department.getCode())
                .assetCount(department.getAssets().size())
                .assets(assetResponses)
                .build();
    }
}
