package com.enterprise.asset_management.department.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.asset_management.department.dto.DepartmentCreateRequest;
import com.enterprise.asset_management.department.dto.DepartmentResponse;
// import com.enterprise.asset_management.department.model.Department;
import com.enterprise.asset_management.department.service.DepartmentService;

@RestController 
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @PostMapping 
    public ResponseEntity<DepartmentResponse> createDepartment(@RequestBody DepartmentCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.createDepartment(request));
    }

    @GetMapping 
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments(){
        return ResponseEntity.ok(departmentService.getAllDepartmentsWithAssets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long id){
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id){
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    } 

}
