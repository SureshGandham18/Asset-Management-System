package com.enterprise.asset_management.asset.dto;

import com.enterprise.asset_management.asset.model.AssetStatus;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class AssetFilterCriteria {
    private String name;
    private String serialNumber;
    private String assetType;
    private AssetStatus status;
}
