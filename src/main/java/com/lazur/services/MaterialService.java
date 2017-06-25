package com.lazur.services;

import com.lazur.entities.materials.Material;
import com.lazur.models.materials.MaterialUpdateModel;
import com.lazur.models.materials.MaterialViewBasicModel;
import com.lazur.models.materials.MaterialViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaterialService {

    List<MaterialViewBasicModel> findAllByMaterial(String currMaterial, String noneParam);

    Material findByMaterialAndType(String finishMaterial, String finishType);

    Material findOneByMaterialAndName(String product, String name);

    Page<MaterialViewModel> findAllByMaterialPage(String name, Pageable pageable);

    Page<MaterialViewModel> findAllByMaterialAndTypePage(String name, String product, Pageable pageable);

    MaterialUpdateModel findMaterialById(Long materialId);
}


