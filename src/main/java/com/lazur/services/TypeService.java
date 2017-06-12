package com.lazur.services;

import com.lazur.models.view.TypeBindingModel;
import com.lazur.models.view.TypeUpdateModel;
import com.lazur.models.view.TypeViewModel;
import com.lazur.models.view.ViewTypeModel;

import java.util.List;

public interface TypeService {


    List<ViewTypeModel> findAllByMaterial(String finishMaterial);

    void save(String product, TypeBindingModel typeBindingModel);

    TypeViewModel findOneById(Long typeId);

    void delete(Long materialId);

    void update(Long materialId, TypeUpdateModel typeUpdateModel);
}


