package com.lazur.serviceImpl;

import com.lazur.entities.specific.SpecificProduct;
import com.lazur.exeptions.SpecificProductNotFoundExeption;
import com.lazur.models.materials.SpecialSubMaterialBindingModel;
import com.lazur.models.materials.SpecialSubMaterialViewModel;
import com.lazur.repositories.SpecificProductRepository;
import com.lazur.services.SpecificProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SpecificProductServiceImpl implements SpecificProductService {

    private static final String COMA_SEPARATOR = ",";

    private final SpecificProductRepository specificProductRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SpecificProductServiceImpl(SpecificProductRepository specificProductRepository,
                                      ModelMapper modelMapper) {
        this.specificProductRepository = specificProductRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SpecialSubMaterialViewModel> findAllProducts() {
        List<SpecificProduct> specificMaterials = this.specificProductRepository.findAll();
        List<SpecialSubMaterialViewModel> specialProductViewModels = new ArrayList<>();
        for (SpecificProduct specificMaterial : specificMaterials) {
            SpecialSubMaterialViewModel specialProductViewModel = this.modelMapper.map(specificMaterial, SpecialSubMaterialViewModel.class);
            specialProductViewModels.add(specialProductViewModel);
        }

        return specialProductViewModels;
    }

    @Override
    public SpecificProduct findByName(String productName) {
        String[] product = productName.split(COMA_SEPARATOR);
        productName = Arrays.stream(product).filter(e -> !e.isEmpty()).findFirst().get();
        SpecificProduct specificProduct = this.specificProductRepository.findByName(productName);
        if (specificProduct == null){
            specificProduct = new SpecificProduct();
            specificProduct.setName(productName);
            specificProduct = this.specificProductRepository.save(specificProduct);
        }

        return specificProduct;
    }

    @Override
    public Page<SpecialSubMaterialViewModel> findAllPageable(Pageable pageable) {
        Page<SpecificProduct> specificMaterials = this.specificProductRepository.findAll(pageable);
        List<SpecialSubMaterialViewModel> specialProductViewModels = new ArrayList<>();
        for (SpecificProduct specificMaterial : specificMaterials) {
            SpecialSubMaterialViewModel specialProductViewModel = this.modelMapper.map(specificMaterial, SpecialSubMaterialViewModel.class);
            specialProductViewModels.add(specialProductViewModel);
        }

        return new PageImpl<>(specialProductViewModels, pageable, specificMaterials.getTotalElements());
    }

    @Override
    public void save(SpecialSubMaterialBindingModel specialSubMaterialBindingModel) {
        SpecificProduct specificProduct = new SpecificProduct();
        specificProduct.setName(specialSubMaterialBindingModel.getName());
        this.specificProductRepository.save(specificProduct);
    }

    @Override
    public SpecialSubMaterialViewModel findById(Long id) {
        SpecificProduct specificProduct = this.specificProductRepository.findOne(id);
        if (specificProduct == null) {
            throw new SpecificProductNotFoundExeption();
        }

        SpecialSubMaterialViewModel specialSubMaterialViewModel = this.modelMapper.map(specificProduct,SpecialSubMaterialViewModel.class);
        return specialSubMaterialViewModel;
    }

    @Override
    public void update(Long id, SpecialSubMaterialBindingModel specialSubMaterialBindingModel) {
        SpecificProduct specificProduct = this.specificProductRepository.findOne(id);
        specificProduct.setName(specialSubMaterialBindingModel.getName());
        this.specificProductRepository.save(specificProduct);
    }

    @Override
    public void delete(Long id) {
        this.specificProductRepository.delete(id);
    }
}
