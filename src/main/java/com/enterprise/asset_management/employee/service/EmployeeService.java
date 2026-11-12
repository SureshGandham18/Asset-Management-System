package com.enterprise.asset_management.employee.service;

import java.util.List;

import com.enterprise.asset_management.employee.dto.EmployeeCreateRequest;
import com.enterprise.asset_management.employee.dto.EmployeeResponse;

public interface  EmployeeService {
    EmployeeResponse createEmployee(EmployeeCreateRequest request);
    EmployeeResponse getEmployeeById(Long id);
    List<EmployeeResponse> getAllEmployees();
    List<EmployeeResponse> getEmployeesByDepartment(Long departmentId);
}
