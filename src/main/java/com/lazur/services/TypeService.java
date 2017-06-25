package com.lazur.services;

import com.lazur.models.materials.TypeBindingModel;
import com.lazur.models.materials.TypeUpdateModel;
import com.lazur.models.materials.TypeViewModel;
import com.lazur.models.materials.ViewTypeModel;

import java.util.List;

public interface TypeService {


    List<ViewTypeModel> findAllByMaterial(String finishMaterial);

    void save(String product, TypeBindingModel typeBindingModel);

    TypeViewModel findOneById(Long typeId);

    void delete(Long materialId);

    void update(Long materialId, TypeUpdateModel typeUpdateModel);
}


