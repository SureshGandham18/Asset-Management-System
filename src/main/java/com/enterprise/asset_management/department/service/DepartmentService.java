package com.enterprise.asset_management.department.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.enterprise.asset_management.department.dto.DepartmentCreateRequest;
import com.enterprise.asset_management.department.dto.DepartmentResponse;

@Service 
public interface DepartmentService {
    DepartmentResponse createDepartment(DepartmentCreateRequest request);
    List<DepartmentResponse> getAllDepartmentsWithAssets();
    DepartmentResponse getDepartmentById(Long id);
    void deleteDepartment(Long id);
}

