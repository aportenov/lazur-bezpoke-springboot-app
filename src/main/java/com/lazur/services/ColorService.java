package com.lazur.services;

import com.lazur.entities.specific.Color;
import com.lazur.models.materials.SpecialSubMaterialViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ColorService  extends SubMaterialService {

    List<SpecialSubMaterialViewModel> findAll();

    Color findByName(String colorName);

    Page<SpecialSubMaterialViewModel> findAllPageable(Pageable pageable);

    SpecialSubMaterialViewModel findById(Long id);
}




