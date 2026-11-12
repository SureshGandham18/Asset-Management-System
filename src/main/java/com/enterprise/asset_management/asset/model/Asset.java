package com.enterprise.asset_management.asset.model;



import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.enterprise.asset_management.department.model.Department;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "assets") 
@Data 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
public class Asset {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "serial_number", nullable = false, unique = true, length = 50)
    private String serialNumber;

    @Column(name = "asset_type", nullable = false, length = 50)
    private String assetType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AssetStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Version 
    @Column(nullable = false)
    private Long version;

    @CreationTimestamp 
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME" )
    private Instant createdAt;

    @UpdateTimestamp 
    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME")
    private Instant updatedAt;
}
