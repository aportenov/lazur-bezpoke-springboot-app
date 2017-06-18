package com.lazur.serviceImpl;

import com.lazur.entities.Material;
import com.lazur.entities.Top;
import com.lazur.exeptions.TopNotFoundExeption;
import com.lazur.models.view.MaterialBindingModel;
import com.lazur.models.view.MaterialUpdateModel;
import com.lazur.models.view.MaterialViewBasicModel;
import com.lazur.repositories.MaterialRepository;
import com.lazur.services.TopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TopServiceImpl implements TopService{

    private final MaterialRepository materialRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TopServiceImpl(MaterialRepository materialRepository,
                          ModelMapper modelMapper) {
        this.materialRepository = materialRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MaterialViewBasicModel> findAll() {
        List<Material> materials = this.materialRepository.findAllWhereNameIs("top");
        List<MaterialViewBasicModel> materialViewBasicModels = new ArrayList<>();
        for (Material material : materials) {
            MaterialViewBasicModel materialViewBasicModel = this.modelMapper.map(material, MaterialViewBasicModel.class);
            materialViewBasicModels.add(materialViewBasicModel);
        }

        return materialViewBasicModels;
    }

    @Override
    @Transactional
    public void save(MaterialBindingModel materialBindingModel) {
        Top top = this.modelMapper.map(materialBindingModel, Top.class);
        Material isExist = this.materialRepository.findOneByMaterialAndName(materialBindingModel.getMaterial(), "top");
        if (isExist == null){

        }
        this.materialRepository.save(top);
    }

    @Override
    public void update(Long materialId, MaterialUpdateModel materialUpdateModel) {
        Material material = this.materialRepository.findOne(materialId);
        if (material == null){
            //throw exeption
        }

        material.setAbbreviation(materialUpdateModel.getAbbreviation());
        material.setMaterial(materialUpdateModel.getMaterial());
        this.materialRepository.save(material);
    }

    @Override
    public void delete(Long materialId) {
        Material top = this.materialRepository.findOne(materialId);
        if (top == null){
            throw new TopNotFoundExeption();
        }

        this.materialRepository.delete(top);
    }
}
