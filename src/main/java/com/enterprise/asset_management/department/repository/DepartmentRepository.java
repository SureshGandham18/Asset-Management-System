package com.enterprise.asset_management.department.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.enterprise.asset_management.department.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByCodeIgnoreCase(String code);

    @Query("SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.assets")
    List<Department> findAllWithAssetsJoinFetch();

    @EntityGraph(attributePaths = {"assets"})
    @Query(" SELECT d FROM Department d WHERE d.id = :id")
    Optional<Department> findByIdWithAssetsGraph(@Param("id") Long id);
}
