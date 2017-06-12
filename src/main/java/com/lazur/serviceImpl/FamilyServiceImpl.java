package com.lazur.serviceImpl;

import com.lazur.entities.Family;
import com.lazur.entities.Model;
import com.lazur.models.view.FamilyBidnignModel;
import com.lazur.models.view.FamilyViewModel;
import com.lazur.repositories.FamilyRepository;
import com.lazur.services.CategoryService;
import com.lazur.services.FamilyService;
import com.lazur.services.ModelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class FamilyServiceImpl implements FamilyService {

    private final ModelMapper modelMapper;
    private final FamilyRepository familyRepository;
    private final CategoryService categoryService;
    private final ModelService modelService;

    @Autowired
    public FamilyServiceImpl(ModelMapper modelMapper,
                             FamilyRepository familyRepository,
                             CategoryService categoryService,
                             ModelService modelService) {
        this.modelMapper = modelMapper;
        this.familyRepository = familyRepository;
        this.categoryService = categoryService;
        this.modelService = modelService;
    }

    @Override
    public Family findFamily(String name, String productModel, String productCategory) {
        Family family = this.familyRepository.findByNameModelAndCategory(name, productModel, productCategory);
        if (family == null) {
            //throw exeption
        }

        return family;
    }

    @Override
    public void save(FamilyBidnignModel familyBidnignModel) {
        Model model = this.modelService.findByCategoryAndModel(familyBidnignModel.getCategory(),familyBidnignModel.getType());

        Family family = new Family();
        family.setName(familyBidnignModel.getName());
        family.setModel(model);
        long count = this.familyRepository.count();
        family.setCode(String.format("%02d", count+1));
        this.familyRepository.save(family);
    }

    @Override
    public List<FamilyViewModel> findAllByCategoryAndModel(String category, String model) {
        List<Family> families = this.familyRepository.findAllWhereCategoryAndModelAre(category, model);
        List<FamilyViewModel> familyViewModels = new ArrayList<>();
        for (Family family : families) {
            FamilyViewModel familyViewModel = this.modelMapper.map(family, FamilyViewModel.class);
            familyViewModels.add(familyViewModel);
        }

        return familyViewModels;
    }

    @Override
    @Transactional
    public FamilyViewModel findByFamily(Long id) {
        Family family = this.familyRepository.findOne(id);
        if (family == null){
            //throw new FamilyNotFoundExeption();
        }

        FamilyViewModel familyViewModel = this.modelMapper.map(family, FamilyViewModel.class);
        return familyViewModel;
    }
}
