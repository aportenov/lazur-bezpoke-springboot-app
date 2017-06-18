package com.lazur.serviceImpl;

import com.lazur.entities.Material;
import com.lazur.models.view.MaterialUpdateModel;
import com.lazur.models.view.MaterialViewBasicModel;
import com.lazur.models.view.MaterialViewModel;
import com.lazur.repositories.MaterialRepository;
import com.lazur.services.MaterialService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService{

    private final ModelMapper modelMapper;
    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialServiceImpl(ModelMapper modelMapper, MaterialRepository materialRepository) {
        this.modelMapper = modelMapper;
        this.materialRepository = materialRepository;
    }

    @Override
    public List<MaterialViewBasicModel> findAllByMaterial(String currMaterial, String noneParam) {
        List<Material> materials = this.materialRepository.findAllWhereNameIsAndNone(currMaterial.toLowerCase(), noneParam);
        List<MaterialViewBasicModel> materialViewBasicModels = new ArrayList<>();
        for (Material material : materials) {
            MaterialViewBasicModel materialViewBasicModel = this.modelMapper.map(material, MaterialViewBasicModel.class);
            materialViewBasicModels.add(materialViewBasicModel);
        }

        return materialViewBasicModels;
    }

    @Override
    public Material findByMaterialAndType(String material, String type) {
       Material foundMaterial = this.materialRepository.findByMaterialAndType(material, type);
       return foundMaterial;
    }

    @Override
    public Material findOneByMaterialAndName(String product, String name) {
        return this.materialRepository.findOneByMaterialAndName(product, name);
    }

    @Override
    public Page<MaterialViewModel> findAllByMaterialPage(String name, Pageable pageable) {
        Page<Material> materials = this.materialRepository.findAllPageWhereMaterialIs(name.toLowerCase(), pageable);
        List<MaterialViewModel> materialViewModels = new ArrayList<>();
        for (Material material : materials) {
            MaterialViewModel materialViewModel = this.modelMapper.map(material, MaterialViewModel.class);
            materialViewModels.add(materialViewModel);
        }

        return new PageImpl<>(materialViewModels, pageable, materials.getTotalElements());
    }

    @Override
    public Page<MaterialViewModel> findAllByMaterialAndTypePage(String name, String product, Pageable pageable) {
        Page<Material> materials = this.materialRepository.findAllPageWhereMaterialAndTypeAre(name.toLowerCase(),product, pageable);
        List<MaterialViewModel> materialViewModels = new ArrayList<>();
        for (Material material : materials) {
            MaterialViewModel materialViewModel = this.modelMapper.map(material, MaterialViewModel.class);
            materialViewModels.add(materialViewModel);
        }

        return new PageImpl<>(materialViewModels, pageable, materials.getTotalElements());
    }

    @Override
    public MaterialUpdateModel findMaterialById(Long materialId) {
        Material material = this.materialRepository.findOne(materialId);
        if (material == null){
            //throw exeption
        }

        MaterialUpdateModel materialUpdateModel = this.modelMapper.map(material,MaterialUpdateModel.class);
        return materialUpdateModel;
    }
}
