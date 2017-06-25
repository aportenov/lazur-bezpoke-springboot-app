package com.lazur.services;


import com.lazur.entities.specific.SpecificProduct;
import com.lazur.models.materials.SpecialSubMaterialViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpecificProductService extends SubMaterialService {

    List<SpecialSubMaterialViewModel> findAllProducts();

    SpecificProduct findByName(String productName);

    Page<SpecialSubMaterialViewModel> findAllPageable(Pageable pageable);

    SpecialSubMaterialViewModel findById(Long id);


}

