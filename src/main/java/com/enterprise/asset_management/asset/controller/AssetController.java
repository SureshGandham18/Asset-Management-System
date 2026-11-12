package com.enterprise.asset_management.asset.controller;


import com.enterprise.asset_management.common.exception.GlobalExceptionHandler;
// import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.asset_management.asset.dto.AssetCreateRequest;
import com.enterprise.asset_management.asset.dto.AssetFilterCriteria;
import com.enterprise.asset_management.asset.dto.AssetResponse;
import com.enterprise.asset_management.asset.dto.PagedResponse;
import com.enterprise.asset_management.asset.model.AssetStatus;
import com.enterprise.asset_management.asset.service.AssetService;

import jakarta.validation.Valid;
// import jakarta.websocket.server.PathParam;

@RestController 
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetService assetService;

    AssetController(AssetService assetService, GlobalExceptionHandler globalExceptionHandler) {
        this.assetService = assetService;
    }

    @PostMapping 
    public ResponseEntity<AssetResponse> createAsset(@Valid @RequestBody AssetCreateRequest request){
        AssetResponse response = assetService.createAsset(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponse> getAssetById(@PathVariable Long id){
       return ResponseEntity.ok(assetService.getAssetById(id));
    }

    @GetMapping 
    public ResponseEntity<PagedResponse<AssetResponse>> getAllAssets(@RequestParam(required = false) String name,
        @RequestParam(required = false) String serialNumber, 
        @RequestParam(required = false) String assetType,
        @RequestParam(required = false) AssetStatus status,
        @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC)  Pageable pageable){
        AssetFilterCriteria criteria = AssetFilterCriteria.builder()
                                    .name(name)
                                    .serialNumber(serialNumber)
                                    .assetType(assetType)
                                    .status(status)
                                    .build();
        return ResponseEntity.ok(assetService.getAllAssets(criteria, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id){
        assetService.deleteAsset(id);
        return ResponseEntity.noContent().build();
    }

}
