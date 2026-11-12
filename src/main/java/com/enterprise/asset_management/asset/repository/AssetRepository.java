package com.enterprise.asset_management.asset.repository;

import com.enterprise.asset_management.asset.model.Asset;
// import com.enterprise.asset_management.asset.model.AssetStatus;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

// @Repository 
public interface AssetRepository extends JpaRepository<Asset, Long>, JpaSpecificationExecutor<Asset> {

    boolean existsBySerialNumberIgnoreCase(String serialNumber);

    Optional<Asset> findBySerialNumber(String serialNumber);
}