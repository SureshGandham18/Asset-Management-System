package com.enterprise.asset_management.asset.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.enterprise.asset_management.asset.dto.AssetFilterCriteria;
import com.enterprise.asset_management.asset.model.Asset;

import jakarta.persistence.criteria.Predicate;

public class AssetSpecification {
    private AssetSpecification(){}
    public static Specification<Asset> withCriteria(AssetFilterCriteria criteria){
        return(root, query, cb) -> {
            if(criteria == null){
                return cb.conjunction();
            }
            List<Predicate> predicates = new ArrayList<>();
            if(criteria.getName() != null && !criteria.getName().isBlank()){
                predicates.add(cb.like(cb.lower(root.get("name")),"%" + criteria.getName().trim().toLowerCase() + "%"));
            }

            if(criteria.getSerialNumber() != null && !criteria.getSerialNumber().isBlank()){
                predicates.add(cb.like(cb.lower(root.get("serialNumber")),"%"+ criteria.getSerialNumber().trim().toLowerCase()+"%"));
            }

            if(criteria.getAssetType() != null && !criteria.getAssetType().isBlank()){
                predicates.add(cb.equal(cb.upper(root.get("assetType")), criteria.getAssetType().trim().toUpperCase()));
            }

            if(criteria.getStatus() != null){
                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
