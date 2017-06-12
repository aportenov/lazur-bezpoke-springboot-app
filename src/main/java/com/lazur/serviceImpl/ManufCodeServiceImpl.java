package com.lazur.serviceImpl;

import com.lazur.entities.specific.ManufCode;
import com.lazur.models.view.SpecialSubMaterialBindingModel;
import com.lazur.models.view.SpecialSubMaterialViewModel;
import com.lazur.repositories.ManufCodeRepository;
import com.lazur.services.ManufCodeService;
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
public class ManufCodeServiceImpl implements ManufCodeService{

    private final ManufCodeRepository manufCodeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ManufCodeServiceImpl(ManufCodeRepository manufCodeRepository,
                                ModelMapper modelMapper) {
        this.manufCodeRepository = manufCodeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SpecialSubMaterialViewModel> findAllManufCodes() {
       List<ManufCode> manufCodes = this.manufCodeRepository.findAll();
       List<SpecialSubMaterialViewModel> manufCodeViewModels = new ArrayList<>();
        for (ManufCode manufCode : manufCodes) {
            SpecialSubMaterialViewModel manufCodeViewModel = this.modelMapper.map(manufCode, SpecialSubMaterialViewModel.class);
            manufCodeViewModels.add(manufCodeViewModel);
        }

        return manufCodeViewModels;
    }

    @Override
    public ManufCode findByName(String manufCodeName) {
        String[] product = manufCodeName.split(",");
        manufCodeName = Arrays.stream(product).filter(e -> !e.isEmpty()).findFirst().get();
        ManufCode manufCode = this.manufCodeRepository.findByName(manufCodeName);
        if (manufCode == null){
            manufCode = new ManufCode();
            manufCode.setName(manufCodeName);
            manufCode = this.manufCodeRepository.save(manufCode);
        }

        return manufCode;
    }

    @Override
    public Page<SpecialSubMaterialViewModel> findAllPageable(Pageable pageable) {
        Page<ManufCode> manufCodes = this.manufCodeRepository.findAll(pageable);
        List<SpecialSubMaterialViewModel> manufCodeViewModels = new ArrayList<>();
        for (ManufCode manufCode : manufCodes) {
            SpecialSubMaterialViewModel manufCodeViewModel = this.modelMapper.map(manufCode, SpecialSubMaterialViewModel.class);
            manufCodeViewModels.add(manufCodeViewModel);
        }

        return new PageImpl<>(manufCodeViewModels, pageable, manufCodes.getTotalElements());
    }

    @Override
    public void save(SpecialSubMaterialBindingModel specialSubMaterialBindingModel) {
        ManufCode manufCode = new ManufCode();
        manufCode.setName(specialSubMaterialBindingModel.getName());
        this.manufCodeRepository.save(manufCode);
    }

    @Override
    public SpecialSubMaterialViewModel findById(Long id) {
        ManufCode manufCode = this.manufCodeRepository.findOne(id);
        if (manufCode == null) {
            //throw ManufCodeNotFoundExeption();
        }

        SpecialSubMaterialViewModel specialSubMaterialViewModel = this.modelMapper.map(manufCode,SpecialSubMaterialViewModel.class);
        return specialSubMaterialViewModel;
    }

    @Override
    public void update(Long id, SpecialSubMaterialBindingModel specialSubMaterialBindingModel) {
        ManufCode manufCode = this.manufCodeRepository.findOne(id);
        manufCode.setName(specialSubMaterialBindingModel.getName());
        this.manufCodeRepository.save(manufCode);
    }

    @Override
    public void delete(Long id) {
        this.manufCodeRepository.delete(id);
    }
}
