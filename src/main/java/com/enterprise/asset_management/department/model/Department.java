package com.enterprise.asset_management.department.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

// import org.hibernate.annotations.Collate;
import org.hibernate.annotations.CreationTimestamp;
// import org.hibernate.annotations.CurrentTimestamp;

import com.enterprise.asset_management.asset.model.Asset;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data 
@Entity 
@Table(name = "departments")
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
@Getter 
public class Department {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Asset> assets = new ArrayList<>();

    @CreationTimestamp 
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @CreationTimestamp 
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
