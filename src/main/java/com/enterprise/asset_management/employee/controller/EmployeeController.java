package com.enterprise.asset_management.employee.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.asset_management.employee.dto.EmployeeCreateRequest;
import com.enterprise.asset_management.employee.dto.EmployeeResponse;
import com.enterprise.asset_management.employee.service.EmployeeService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }
    @PostMapping 
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable  Long id){
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(@RequestParam(required = false) Long departmentId){
        if(departmentId != null){
            return ResponseEntity.ok(employeeService.getEmployeesByDepartment(departmentId));
        }
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
}
