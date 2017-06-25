package com.lazur.services;


import com.lazur.entities.specific.ManufCode;
import com.lazur.models.materials.SpecialSubMaterialViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ManufCodeService  extends SubMaterialService {

    List<SpecialSubMaterialViewModel> findAllManufCodes();

    ManufCode findByName(String manufCodeName);

    Page<SpecialSubMaterialViewModel> findAllPageable(Pageable pageable);

    SpecialSubMaterialViewModel findById(Long id);
}



