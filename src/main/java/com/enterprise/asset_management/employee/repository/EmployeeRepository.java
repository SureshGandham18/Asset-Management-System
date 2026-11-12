package com.enterprise.asset_management.employee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

import com.enterprise.asset_management.employee.model.Employee;
// import com.enterprise.asset_management.department.model.Department;


// @Repository 
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmailIgnoreCase(String email);
    Optional<Employee> findByEmailIgnoreCase(String email);

    @EntityGraph(attributePaths = {"department"})
    List<Employee> findByDepartmentId(Long departmentId);

    @Override 
    @EntityGraph(attributePaths = {"department"})
    List<Employee> findAll();
}
