package com.lazur.services;


import com.lazur.entities.specific.Manufacturer;
import com.lazur.models.materials.SpecialSubMaterialViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ManufacturerService  extends SubMaterialService {

    List<SpecialSubMaterialViewModel> findAllManufactorers();

    Manufacturer findByName(String manufactorerName);

    Page<SpecialSubMaterialViewModel> findAllPageable(Pageable pageable);

    SpecialSubMaterialViewModel findById(Long id);
}




