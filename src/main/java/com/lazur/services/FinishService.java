package com.lazur.services;

import com.lazur.models.view.MaterialBindingModel;
import com.lazur.models.view.MaterialUpdateModel;
import com.lazur.models.view.MaterialViewBasicModel;

import java.util.List;

public interface FinishService {

    List<MaterialViewBasicModel> findAll();

    void save(MaterialBindingModel materialBindingModel);

    void update(Long materialId, MaterialUpdateModel materialUpdateModel);

    void delete(Long materialId);
}


