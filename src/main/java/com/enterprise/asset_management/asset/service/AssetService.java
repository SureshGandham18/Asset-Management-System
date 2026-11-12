package com.enterprise.asset_management.asset.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

// import java.util.List;

// import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;

import com.enterprise.asset_management.asset.dto.AssetCreateRequest;
import com.enterprise.asset_management.asset.dto.AssetFilterCriteria;
import com.enterprise.asset_management.asset.dto.AssetResponse;
import com.enterprise.asset_management.asset.dto.PagedResponse;

@Service 
public interface AssetService {
    AssetResponse createAsset(AssetCreateRequest request);
    AssetResponse getAssetById(Long id);
    PagedResponse<AssetResponse> getAllAssets(AssetFilterCriteria criteria, Pageable pageable);
    void deleteAsset(Long id);
} 
