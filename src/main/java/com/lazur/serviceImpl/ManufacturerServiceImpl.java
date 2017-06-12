package com.lazur.serviceImpl;

import com.lazur.entities.specific.Manufacturer;
import com.lazur.models.view.SpecialSubMaterialBindingModel;
import com.lazur.models.view.SpecialSubMaterialViewModel;
import com.lazur.repositories.ManufacturerRepository;
import com.lazur.services.ManufacturerService;
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
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository,
                                   ModelMapper modelMapper) {
        this.manufacturerRepository = manufacturerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SpecialSubMaterialViewModel> findAllManufactorers() {
        List<Manufacturer> manufacturers = this.manufacturerRepository.findAll();
        List<SpecialSubMaterialViewModel> manufacturerViewModels = new ArrayList<>();
        for (Manufacturer manufacturer : manufacturers) {
            SpecialSubMaterialViewModel manufacturerViewModel = this.modelMapper.map(manufacturer, SpecialSubMaterialViewModel.class);
            manufacturerViewModels.add(manufacturerViewModel);
        }

        return manufacturerViewModels;
    }

    @Override
    public Manufacturer findByName(String manufactorerName) {
        String[] product = manufactorerName.split(",");
        manufactorerName = Arrays.stream(product).filter(e -> !e.isEmpty()).findFirst().get();
        Manufacturer manufacturer = this.manufacturerRepository.findByName(manufactorerName);
        if (manufacturer == null){
            manufacturer = new Manufacturer();
            manufacturer.setName(manufactorerName);
            manufacturer = this.manufacturerRepository.save(manufacturer);
        }

        return manufacturer;
    }

    @Override
    public Page<SpecialSubMaterialViewModel> findAllPageable(Pageable pageable) {
        Page<Manufacturer> manufacturers = this.manufacturerRepository.findAll(pageable);
        List<SpecialSubMaterialViewModel> manufacturerViewModels = new ArrayList<>();
        for (Manufacturer manufacturer : manufacturers) {
            SpecialSubMaterialViewModel manufacturerViewModel = this.modelMapper.map(manufacturer, SpecialSubMaterialViewModel.class);
            manufacturerViewModels.add(manufacturerViewModel);
        }

        return new PageImpl<>(manufacturerViewModels, pageable, manufacturers.getTotalElements());
    }

    @Override
    public void save(SpecialSubMaterialBindingModel specialSubMaterialBindingModel) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(specialSubMaterialBindingModel.getName());
        this.manufacturerRepository.save(manufacturer);
    }

    @Override
    public SpecialSubMaterialViewModel findById(Long id) {
        Manufacturer manufacturer = this.manufacturerRepository.findOne(id);
        if (manufacturer == null) {
            //throw ManufacturerNotFoundExeption();
        }

        SpecialSubMaterialViewModel specialSubMaterialViewModel = this.modelMapper.map(manufacturer,SpecialSubMaterialViewModel.class);
        return specialSubMaterialViewModel;
    }

    @Override
    public void update(Long id, SpecialSubMaterialBindingModel specialSubMaterialBindingModel) {
        Manufacturer manufacturer = this.manufacturerRepository.findOne(id);
        manufacturer.setName(specialSubMaterialBindingModel.getName());
        this.manufacturerRepository.save(manufacturer);
    }

    @Override
    public void delete(Long id) {
        this.manufacturerRepository.delete(id);
    }
}
