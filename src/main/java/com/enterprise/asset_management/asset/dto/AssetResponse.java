package com.enterprise.asset_management.asset.dto;

import java.time.Instant;

import com.enterprise.asset_management.asset.model.AssetStatus;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class AssetResponse {
    private Long id;
    private String name;
    private String serialNumber;
    private String assetType;
    private AssetStatus status;
    private Instant createdAt;
}
