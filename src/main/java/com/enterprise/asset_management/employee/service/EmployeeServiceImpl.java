package com.enterprise.asset_management.employee.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enterprise.asset_management.common.exception.DuplicateResourceException;
import com.enterprise.asset_management.common.exception.ResourceNotFoundException;
import com.enterprise.asset_management.department.model.Department;
import com.enterprise.asset_management.department.repository.DepartmentRepository;
import com.enterprise.asset_management.employee.dto.EmployeeCreateRequest;
import com.enterprise.asset_management.employee.dto.EmployeeResponse;
import com.enterprise.asset_management.employee.model.Employee;
import com.enterprise.asset_management.employee.repository.EmployeeRepository;

@Service 
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository){
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }


    @Transactional
    public EmployeeResponse createEmployee(EmployeeCreateRequest request){
        if(employeeRepository.existsByEmailIgnoreCase(request.getEmail().trim())){
            throw new DuplicateResourceException("Employee with email '"+request.getEmail()+"' already exists.");
        }

        Department department = departmentRepository.findById(request.getDepartmentId()).
            orElseThrow(()-> new ResourceNotFoundException("Department not found with ID: "+ request.getDepartmentId()));

        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .department(department)
                .build();

        Employee saved = employeeRepository.save(employee);
        return mapToResponse(saved);

    }

    public EmployeeResponse getEmployeeById(Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        return  mapToResponse(employee);
    }

    public List<EmployeeResponse> getAllEmployees(){
        return employeeRepository.findAll().stream()
            .map(this::mapToResponse).toList();
    }

    public List<EmployeeResponse> getEmployeesByDepartment(Long departmentId){
        if(!departmentRepository.existsById(departmentId)){
            throw new ResourceNotFoundException("Department not found with ID: "+departmentId);
        }
        return employeeRepository.findByDepartmentId(departmentId).stream()
            .map(this::mapToResponse)
            .toList();
    }

    private EmployeeResponse mapToResponse(Employee employee){
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .departmentId(employee.getDepartment().getId())
                .departmentName(employee.getDepartment().getName())
                .createdAt(employee.getCreatedAt())
                .build();
    }


    
}
