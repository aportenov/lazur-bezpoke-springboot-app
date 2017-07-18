package com.lazur.serviceImpl;


import com.lazur.entities.materials.Material;
import com.lazur.entities.types.Type;
import com.lazur.exeptions.MaterialNotFoundExeption;
import com.lazur.exeptions.TypeNotFoundExeption;
import com.lazur.models.materials.TypeBindingModel;
import com.lazur.models.materials.TypeUpdateModel;
import com.lazur.models.materials.TypeViewModel;
import com.lazur.models.materials.ViewTypeModel;
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

    private static final String FRAME = "frame";
    private static final int FIRST_LETTER = 65;
    private static final int FIRST_NUMBER = 49;
    private static final int TOTAL_CODE_LENGHT = 9;

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
            throw new MaterialNotFoundExeption();
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
            throw new TypeNotFoundExeption();
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
            throw new TypeNotFoundExeption();
        }

        type.setName(typeUpdateModel.getName());
        type.setCode(typeUpdateModel.getCode());
        this.typeRepository.save(type);
    }


    private char getCode(int size, String type) {
        char typeCode;
        if (type.equalsIgnoreCase(FRAME)){
            typeCode = (char)(size + FIRST_LETTER);
        }else {
           typeCode = (char)((size % TOTAL_CODE_LENGHT) + FIRST_NUMBER);
        }

        return typeCode;
    }
}
