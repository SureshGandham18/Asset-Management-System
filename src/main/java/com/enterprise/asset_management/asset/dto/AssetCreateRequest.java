package com.enterprise.asset_management.asset.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class AssetCreateRequest {
    @NotBlank(message = "Asset name is required")
    @Size (min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Serial number is required")
    @Size (min = 3, max = 50, message = "Serial number must be between 3 and 50 characters")
    private String serialNumber;

    @NotBlank(message = "Asset type is required Eg.(LAPTOP, SERVER, MONITOR)")
    private String assetType;
}
