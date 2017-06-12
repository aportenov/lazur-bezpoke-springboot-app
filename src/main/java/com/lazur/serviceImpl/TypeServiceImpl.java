package com.lazur.serviceImpl;


import com.lazur.entities.Material;
import com.lazur.entities.Type;
import com.lazur.models.view.TypeBindingModel;
import com.lazur.models.view.TypeUpdateModel;
import com.lazur.models.view.TypeViewModel;
import com.lazur.models.view.ViewTypeModel;
import com.lazur.repositories.TypeRepository;
import com.lazur.services.MaterialService;
import com.lazur.services.TypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService{

    private final TypeRepository typeRepository;
    private final ModelMapper modelMapper;
    private final MaterialService materialService;

    @Autowired
    public TypeServiceImpl(TypeRepository typeRepository,
                           ModelMapper modelMapper,
                           MaterialService materialService) {
        this.typeRepository = typeRepository;
        this.modelMapper = modelMapper;
        this.materialService = materialService;
    }

    @Override
    public List<ViewTypeModel> findAllByMaterial(String finishMaterial) {
        List<Type> types = this.typeRepository.findAllWhereMaterialIs(finishMaterial);
        List<ViewTypeModel> viewTypeModels = new ArrayList<>();
        for (Type type : types) {
            ViewTypeModel viewTypeModel = this.modelMapper.map(type, ViewTypeModel.class);
            viewTypeModels.add(viewTypeModel);
        }

        return viewTypeModels;
    }

    @Override
    @Transactional
    public void save(String product, TypeBindingModel typeBindingModel) {
        Type type = new Type();
        type.setName(typeBindingModel.getName());
        List<Type> types = this.typeRepository.findAllWhereMaterialIs(product);
        Material material = this.materialService.findOneByMaterialAndName(product, typeBindingModel.getType().toLowerCase());
        if (material == null) {

        }

        type.addMaterials(material);
        char typeCode = getCode(types.size(),typeBindingModel.getType());
        String code = String.format("%s%s", material.getAbbreviation(), typeCode);
        type.setCode(code);
        this.typeRepository.save(type);
    }

    @Override
    public TypeViewModel findOneById(Long typeId) {
        Type type = this.typeRepository.findOne(typeId);
        if (type == null){
            //throw exeption
        }

        TypeViewModel typeViewModel = this.modelMapper.map(type, TypeViewModel.class);
        return typeViewModel;
    }

    @Override
    public void delete(Long materialId) {
        this.typeRepository.delete(materialId);
    }

    @Override
    public void update(Long materialId, TypeUpdateModel typeUpdateModel) {
        Type type = this.typeRepository.findOne(materialId);
        if (type == null){
            //throw exeption
        }

        type.setName(typeUpdateModel.getName());
        type.setCode(typeUpdateModel.getCode());
        this.typeRepository.save(type);
    }


    private char getCode(int size, String type) {
        char typeCode;
        if (type.equalsIgnoreCase("frame")){
            typeCode = (char)(size + 65);
        }else {
           typeCode = (char)((size % 9) + 49);
        }

        return typeCode;
    }
}
