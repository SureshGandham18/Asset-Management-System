package com.enterprise.asset_management.asset.service;

// import java.time.Instant;
// import java.util.List;
// import java.util.Map;
// import java.util.concurrent.ConcurrentHashMap;
// import java.util.concurrent.atomic.AtomicLong;

// import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enterprise.asset_management.asset.dto.AssetCreateRequest;
import com.enterprise.asset_management.asset.dto.AssetFilterCriteria;
import com.enterprise.asset_management.asset.dto.AssetResponse;
import com.enterprise.asset_management.asset.dto.PagedResponse;
import com.enterprise.asset_management.asset.model.Asset;
import com.enterprise.asset_management.asset.model.AssetStatus;
import com.enterprise.asset_management.asset.repository.AssetRepository;
import com.enterprise.asset_management.asset.repository.AssetSpecification;
import com.enterprise.asset_management.common.exception.DuplicateResourceException;
import com.enterprise.asset_management.common.exception.ResourceNotFoundException;

@Service 
@Transactional(readOnly = true) 
public class AssetServiceImpl implements AssetService {
    // private final Map<Long, Asset> assetStorage = new ConcurrentHashMap<>();
    // private final AtomicLong idGenerator = new AtomicLong(1);

    private final AssetRepository assetRepository;

    public AssetServiceImpl(AssetRepository assetRepository){
        this.assetRepository = assetRepository;
    }

    // createAsset
    @Transactional 
    public AssetResponse createAsset(AssetCreateRequest assetReq){
        // boolean serialExist = assetStorage.values().stream().anyMatch(a->a.getSerialNumber().equalsIgnoreCase(assetReq.getSerialNumber()));
        if(assetRepository.existsBySerialNumberIgnoreCase(assetReq.getSerialNumber())){
            throw new DuplicateResourceException("The serial number with "+assetReq.getSerialNumber()+" is already exists.");
        }
        Asset asset = Asset.builder()
                    .name(assetReq.getName().trim())
                    .serialNumber(assetReq.getSerialNumber().trim())
                    .assetType(assetReq.getAssetType().trim())
                    .status(AssetStatus.AVAILABLE)
                    .build();
        // assetStorage.put(newId,asset);
        assetRepository.save(asset);
        return mapToResponse(asset);

    }

    // getAssetById
    public AssetResponse getAssetById(Long id){
        // Asset asset = assetStorage.get(id);
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID :"+id));
        return  mapToResponse(asset);
    }

    // getAllAssets
    public PagedResponse<AssetResponse> getAllAssets(AssetFilterCriteria criteria, Pageable pageable){
        Specification<Asset> spec = AssetSpecification.withCriteria(criteria);
        Page<Asset> assetPage = assetRepository.findAll(spec, pageable);
        Page<AssetResponse> responsePage = assetPage.map(this::mapToResponse);
        return PagedResponse.of(responsePage);
    }

    // deleteAsset
    @Transactional
    public void deleteAsset(Long id) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Asset not found with ID: " + id
                )
            );
        assetRepository.delete(asset);
    }

    public AssetResponse mapToResponse(Asset asset){
        AssetResponse assetResponse = AssetResponse.builder()
                                    .id(asset.getId())
                                    .name(asset.getName())
                                    .serialNumber(asset.getSerialNumber())
                                    .assetType(asset.getAssetType())
                                    .status(asset.getStatus())
                                    .createdAt(asset.getCreatedAt())
                                    .build();
        return assetResponse;
    }
}
