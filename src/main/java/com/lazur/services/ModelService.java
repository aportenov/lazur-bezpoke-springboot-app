package com.lazur.services;


import com.lazur.entities.Model;
import com.lazur.models.categories.CategoryAndModelUpdateModel;
import com.lazur.models.models.ModelBindingModel;
import com.lazur.models.models.ModelViewModel;

import java.util.List;

public interface ModelService {

    void save(ModelBindingModel modelBindingModel);

    List<ModelViewModel> findAllModelsByCategory(String category);

    Model findByCategoryAndModel(String category, String model);

    void updateCodes(String category, String oldCode, String code);

    void update(Long modelId, CategoryAndModelUpdateModel categoryAndModelUpdateModel);

    void delete(Long modelId);
}




