package com.lazur.services;


import com.lazur.entities.Model;
import com.lazur.models.view.CategoryAndModelUpdateModel;
import com.lazur.models.view.ModelBindingModel;
import com.lazur.models.view.ModelViewModel;

import java.util.List;

public interface ModelService {

    void save(ModelBindingModel modelBindingModel);

    List<ModelViewModel> findAllModelsByCategory(String category);

    Model findByCategoryAndModel(String category, String model);

    void updateCodes(String category, String oldCode, String code);

    void update(Long modelId, CategoryAndModelUpdateModel categoryAndModelUpdateModel);
}



