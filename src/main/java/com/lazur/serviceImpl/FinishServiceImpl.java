package com.lazur.serviceImpl;

import com.lazur.entities.materials.Finish;
import com.lazur.entities.materials.Material;
import com.lazur.exeptions.FinishNotFoundExeption;
import com.lazur.models.materials.MaterialBindingModel;
import com.lazur.models.materials.MaterialUpdateModel;
import com.lazur.models.materials.MaterialViewBasicModel;
import com.lazur.repositories.MaterialRepository;
import com.lazur.services.FinishService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class FinishServiceImpl implements FinishService{

    private final MaterialRepository materialRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FinishServiceImpl(MaterialRepository materialRepository, ModelMapper modelMapper) {
        this.materialRepository = materialRepository;

        this.modelMapper = modelMapper;
    }

    @Override
    public List<MaterialViewBasicModel> findAll() {
       List<Material> materials = this.materialRepository.findAllWhereNameIs("finish");
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
        Finish finish = this.modelMapper.map(materialBindingModel,Finish.class);
        Material isExist = this.materialRepository.findOneByMaterialAndName(materialBindingModel.getMaterial(), "finish");
        if (isExist == null){
            this.materialRepository.save(finish);
        }


    }

    @Override
    public void update(Long materialId, MaterialUpdateModel materialUpdateModel) {
        Material material = this.materialRepository.findOne(materialId);
        if (material == null){
            throw new FinishNotFoundExeption();
        }

        material.setAbbreviation(materialUpdateModel.getAbbreviation());
        material.setMaterial(materialUpdateModel.getMaterial());
        this.materialRepository.save(material);
    }

    @Override
    public void delete(Long materialId) {
        this.materialRepository.delete(materialId);
    }

}
