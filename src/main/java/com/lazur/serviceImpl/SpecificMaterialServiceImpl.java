package com.lazur.serviceImpl;

import com.lazur.entities.specific.*;
import com.lazur.exeptions.SpecificMaterialNotFoundExeption;
import com.lazur.models.materials.SpecificBindingModel;
import com.lazur.models.materials.SpecificMaterialViewBasicModel;
import com.lazur.repositories.SpecificMaterialRepository;
import com.lazur.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpecificMaterialServiceImpl implements SpecificMaterialService {

    private static final String NONE_CODE = "00";

    private final SpecificMaterialRepository specificMaterialRepository;
    private final ModelMapper modelMapper;
    private final SpecificProductService specificProductService;
    private final ColorService colorService;
    private final ManufacturerService manufacturerService;
    private final ManufCodeService manufCodeService;

    @Autowired
    public SpecificMaterialServiceImpl(SpecificMaterialRepository specificMaterialRepository,
                                       ModelMapper modelMapper,
                                       SpecificProductService specificProductService,
                                       ColorService colorService,
                                       ManufacturerService manufacturerService,
                                       ManufCodeService manufCodeService) {
        this.specificMaterialRepository = specificMaterialRepository;
        this.modelMapper = modelMapper;
        this.specificProductService = specificProductService;
        this.colorService = colorService;
        this.manufacturerService = manufacturerService;
        this.manufCodeService = manufCodeService;
    }


    @Override
    public List<SpecificMaterialViewBasicModel> findAll() {
       List<SpecificMaterial> specificMaterials = this.specificMaterialRepository.findAllMaterials();
       List<SpecificMaterialViewBasicModel> specificMaterialViewBasicModels = new ArrayList<>();
        for (SpecificMaterial specificMaterial : specificMaterials) {
            SpecificMaterialViewBasicModel specificMaterialViewModel = this.modelMapper.map(specificMaterial, SpecificMaterialViewBasicModel.class);
            specificMaterialViewBasicModels.add(specificMaterialViewModel);
        }

        return specificMaterialViewBasicModels;
    }

    @Override
    @Transactional
    public void save(SpecificBindingModel specificBindingModel) {
        SpecificProduct specificProduct = this.specificProductService.findByName(specificBindingModel.getSpecificProductName());
        Color color = this.colorService.findByName(specificBindingModel.getColorName());
        Manufacturer manufacturer = this.manufacturerService.findByName(specificBindingModel.getManufacturerName());
        ManufCode manufCode = this.manufCodeService.findByName(specificBindingModel.getManufCodeName());
        SpecificMaterial specificMaterial = new SpecificMaterial();
        specificMaterial.setColor(color);
        specificMaterial.setManufacturer(manufacturer);
        specificMaterial.setManufCode(manufCode);
        specificMaterial.setSpecificProduct(specificProduct);;
        specificMaterial.setCode(specificBindingModel.getCode());
        this.specificMaterialRepository.save(specificMaterial);

    }

    @Override
    public Page<SpecificMaterialViewBasicModel> findAllPageable(Pageable pageable) {
        Page<SpecificMaterial> specificMaterials = this.specificMaterialRepository.findAll(NONE_CODE, pageable);
        List<SpecificMaterialViewBasicModel> materialViewBasicModels = new ArrayList<>();
        for (SpecificMaterial specificMaterial : specificMaterials) {
            SpecificMaterialViewBasicModel specificMaterialViewBasicModel = this.modelMapper.map(specificMaterial, SpecificMaterialViewBasicModel.class);
            materialViewBasicModels.add(specificMaterialViewBasicModel);
        }

        return new PageImpl<>(materialViewBasicModels, pageable, specificMaterials.getTotalElements());
    }

    @Override
    public SpecificMaterialViewBasicModel findOneById(Long id) {
       SpecificMaterial specificMaterial = this.specificMaterialRepository.findOne(id);
       if (specificMaterial == null){
           throw new SpecificMaterialNotFoundExeption();
       }
       SpecificMaterialViewBasicModel specificMaterialViewBasicModel = this.modelMapper.map(specificMaterial, SpecificMaterialViewBasicModel.class);
       return specificMaterialViewBasicModel;
    }

    @Override
    public void update(Long id, SpecificBindingModel specificBindingModel) {
        SpecificMaterial specificMaterial = this.specificMaterialRepository.findOne(id);
        if (specificMaterial == null) {
            throw new SpecificMaterialNotFoundExeption();
        }

        SpecificProduct specificProduct = this.specificProductService.findByName(specificBindingModel.getSpecificProductName());
        Color color = this.colorService.findByName(specificBindingModel.getColorName());
        Manufacturer manufacturer = this.manufacturerService.findByName(specificBindingModel.getManufacturerName());
        ManufCode manufCode = this.manufCodeService.findByName(specificBindingModel.getManufCodeName());
        specificMaterial.setColor(color);
        specificMaterial.setManufacturer(manufacturer);
        specificMaterial.setManufCode(manufCode);
        specificMaterial.setSpecificProduct(specificProduct);
        specificMaterial.setCode(specificBindingModel.getCode());
        this.specificMaterialRepository.save(specificMaterial);
    }

    @Override
    public void delete(Long id) {
        this.specificMaterialRepository.delete(id);
    }

    @Override
    public SpecificMaterial findEntityById(Long specificMaterialId) {
        SpecificMaterial specificMaterial = this.specificMaterialRepository.findOne(specificMaterialId);
        if (specificMaterial == null){
            throw new SpecificMaterialNotFoundExeption();
        }

        return specificMaterial;
    }

}
