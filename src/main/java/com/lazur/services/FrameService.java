package com.lazur.services;

import com.lazur.models.materials.MaterialBindingModel;
import com.lazur.models.materials.MaterialUpdateModel;
import com.lazur.models.materials.MaterialViewBasicModel;

import java.util.List;

public interface FrameService {

    List<MaterialViewBasicModel> findAll();

    void save(MaterialBindingModel materialBindingModel);

    void update(Long materialId, MaterialUpdateModel materialUpdateModel);

    void delete(Long materialId);
}


