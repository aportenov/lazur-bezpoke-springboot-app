package com.lazur.serviceImpl;

import com.lazur.entities.specific.Color;
import com.lazur.models.view.SpecialSubMaterialBindingModel;
import com.lazur.models.view.SpecialSubMaterialViewModel;
import com.lazur.repositories.ColorRepository;
import com.lazur.services.ColorService;
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
public class ColorServiceImpl implements ColorService{

    private final ColorRepository colorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ColorServiceImpl(ColorRepository colorRepository,
                            ModelMapper modelMapper) {
        this.colorRepository = colorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SpecialSubMaterialViewModel> findAll() {
        List<Color> colors = this.colorRepository.findAll();
        List<SpecialSubMaterialViewModel> specialSubMaterialViewModels = new ArrayList<>();
        for (Color color :  colors) {
            SpecialSubMaterialViewModel specialSubMaterialViewModel = this.modelMapper.map(color, SpecialSubMaterialViewModel.class);
            specialSubMaterialViewModels.add(specialSubMaterialViewModel);
        }

        return specialSubMaterialViewModels;
    }

    @Override
    public Color findByName(String colorName) {
        String[] product = colorName.split(",");
        colorName = Arrays.stream(product).filter(e -> !e.isEmpty()).findFirst().get();
        Color color = this.colorRepository.findByName(colorName);
        if (color == null){
            color = new Color();
            color.setName(colorName);
            color = this.colorRepository.save(color);
        }

        return color;
    }

    @Override
    public Page<SpecialSubMaterialViewModel> findAllPageable(Pageable pageable) {
        Page<Color> colors = this.colorRepository.findAll(pageable);
        List<SpecialSubMaterialViewModel> specialSubMaterialViewModels = new ArrayList<>();
        for (Color color :  colors) {
            SpecialSubMaterialViewModel specialSubMaterialViewModel = this.modelMapper.map(color, SpecialSubMaterialViewModel.class);
            specialSubMaterialViewModels.add(specialSubMaterialViewModel);
        }

        return new PageImpl<>(specialSubMaterialViewModels, pageable, colors.getTotalElements());
    }

    @Override
    public void save(SpecialSubMaterialBindingModel specialSubMaterialBindingModel) {
        Color color = new Color();
        color.setName(specialSubMaterialBindingModel.getName());
        this.colorRepository.save(color);
    }

    @Override
    public SpecialSubMaterialViewModel findById(Long id) {
        Color color = this.colorRepository.findOne(id);
        if (color == null) {
            //throw ColorNotFoundExeption();
        }

        SpecialSubMaterialViewModel specialSubMaterialViewModel = this.modelMapper.map(color,SpecialSubMaterialViewModel.class);
        return specialSubMaterialViewModel;
    }

    @Override
    public void update(Long id, SpecialSubMaterialBindingModel specialSubMaterialBindingModel) {
        Color color = this.colorRepository.findOne(id);
        color.setName(specialSubMaterialBindingModel.getName());
        this.colorRepository.save(color);
    }

    @Override
    public void delete(Long id) {
        this.colorRepository.delete(id);
    }
}
