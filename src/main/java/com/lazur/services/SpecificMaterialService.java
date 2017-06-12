package com.lazur.services;

import com.lazur.entities.specific.SpecificMaterial;
import com.lazur.models.view.SpecificBindingModel;
import com.lazur.models.view.SpecificMaterialViewBasicModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpecificMaterialService {

    List<SpecificMaterialViewBasicModel> findAll();

    void save(SpecificBindingModel specificBindingModel);

    Page<SpecificMaterialViewBasicModel> findAllPageable(Pageable pageable);

    SpecificMaterialViewBasicModel findOneById(Long id);

    void update(Long id, SpecificBindingModel specificBindingModel);

    void delete(Long id);

    SpecificMaterial findEntityById(Long specificMaterial);
}






