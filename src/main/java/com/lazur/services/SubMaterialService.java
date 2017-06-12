package com.lazur.services;

import com.lazur.models.view.SpecialSubMaterialBindingModel;

public interface SubMaterialService {

    void save(SpecialSubMaterialBindingModel specialSubMaterialBindingModel);

    void update(Long id, SpecialSubMaterialBindingModel specialSubMaterialBindingModel);

    void delete(Long id);
}

