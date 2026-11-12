package com.enterprise.asset_management.department.dto;

import java.util.List;

import com.enterprise.asset_management.asset.dto.AssetResponse;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class DepartmentResponse {
    private Long id;
    private String name;
    private String code;
    private int assetCount;
    private List<AssetResponse> assets;
}
