package com.lazur.serviceImpl;

import com.lazur.entities.materials.Frame;
import com.lazur.entities.materials.Material;
import com.lazur.exeptions.FrameNotFoundExeption;
import com.lazur.models.materials.MaterialBindingModel;
import com.lazur.models.materials.MaterialUpdateModel;
import com.lazur.models.materials.MaterialViewBasicModel;
import com.lazur.repositories.MaterialRepository;
import com.lazur.services.FrameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class FrameServiceImpl implements FrameService{
    private static final String FRAME = "frame";

    private final ModelMapper modelMapper;
    private final MaterialRepository materialRepository;

    @Autowired
    public FrameServiceImpl(ModelMapper modelMapper,
                            MaterialRepository materialRepository) {
        this.modelMapper = modelMapper;
        this.materialRepository = materialRepository;
    }


    @Override
    public List<MaterialViewBasicModel> findAll() {
       List<Material> materials = this.materialRepository.findAllWhereNameIs(FRAME);
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
        Frame frame = this.modelMapper.map(materialBindingModel, Frame.class);
        Material isExist = this.materialRepository.findOneByMaterialAndName(materialBindingModel.getMaterial(), FRAME);
        if (isExist == null){
            this.materialRepository.save(frame);
        }
    }

    @Override
    public void update(Long materialId, MaterialUpdateModel materialUpdateModel) {
        Material material = this.materialRepository.findOne(materialId);
        if (material == null){
            throw new FrameNotFoundExeption();
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
