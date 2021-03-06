package com.flyingwillow.restaurant.mapper;

import com.flyingwillow.restaurant.domain.Material;

import java.util.List;
import java.util.Map;

/**
 * Created by liuxuhui on 2017/9/7.
 */
public interface MaterialMapper {

    public List<Material> getMaterialList(Map<String, Object> params);

    public Integer getMaterialCount(Map<String, Object> params);

    public Material getMaterialById(Integer materialId);

    public void saveMaterial(Material material);

    public void updateMaterial(Material material);

    public void deleteMaterial(Integer id);

    public void deleteMaterialByIds(Map<String,Object> param);
}
