package com.lazur.serviceImpl;

import com.lazur.entities.Category;
import com.lazur.entities.CategoryCode;
import com.lazur.entities.Model;
import com.lazur.exeptions.ModelNotFoundExeption;
import com.lazur.models.view.CategoryAndModelUpdateModel;
import com.lazur.models.view.ModelBindingModel;
import com.lazur.models.view.ModelViewModel;
import com.lazur.repositories.ModelRepository;
import com.lazur.services.CategoryCodeService;
import com.lazur.services.CategoryService;
import com.lazur.services.ModelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelServiceImpl implements ModelService{

    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;
    private final CategoryCodeService categoryCodeService;
    private final CategoryService categoryService;

    @Autowired
    public ModelServiceImpl(ModelRepository modelRepository,
                            ModelMapper modelMapper,
                            CategoryCodeService categoryCodeService,
                            CategoryService categoryService) {
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
        this.categoryCodeService = categoryCodeService;
        this.categoryService = categoryService;
    }

    @Override
    public void save(ModelBindingModel modelBindingModel) {
        List<CategoryCode> avaiableCodes = this.categoryCodeService.findAllByCategory(modelBindingModel.getType());
        List<String> codes = new ArrayList<>(avaiableCodes.stream().map(e -> e.getCode()).collect(Collectors.toList()));
        List<Model> modelList = this.modelRepository.findAllByType(codes);
        int codeNumber = (modelList.size() % 9 ) + 1 ;
        String modelCode = avaiableCodes.get(avaiableCodes.size()-1).getCode() + String.valueOf(codeNumber);
        Model model = new Model();
        model.setName(modelBindingModel.getName());
        model.setCode(modelCode);
        Category category = this.categoryService.findCategory(modelBindingModel.getType());
        model.setCategory(category);
        this.modelRepository.save(model);
    }

    @Override
    public List<ModelViewModel> findAllModelsByCategory(String category) {
        List<Model> models = this.modelRepository.findAllByCategory(category);
        List<ModelViewModel> modelViewModels = new ArrayList<>();
        for (Model model : models) {
            ModelViewModel modelViewModel = this.modelMapper.map(model, ModelViewModel.class);
            modelViewModels.add(modelViewModel);
        }

        return modelViewModels;
    }

    @Override
    public Model findByCategoryAndModel(String category, String model) {
        Model foundModel = this.modelRepository.findModelByCategoryAndModelName(category, model);
        return foundModel;
    }

    @Override
    public void updateCodes(String category, String oldCode, String code) {
        List<Model> models = this.modelRepository.findAllByCategory(category);
        for (Model model : models) {
            String modelCode = model.getCode();
            if (modelCode.toUpperCase().startsWith(oldCode.toUpperCase())){
                modelCode = code.toUpperCase() + modelCode.substring(1);
                model.setCode(modelCode);
                this.modelRepository.save(model);
            }
        }
    }

    @Override
    public void update(Long modelId, CategoryAndModelUpdateModel categoryAndModelUpdateModel) {
        Model model = this.modelRepository.findOne(modelId);
        if (model == null){
            //throw new ModelNotFoundExeption();
        }

        String code = String.format("%s%s", categoryAndModelUpdateModel.getOldCode(), categoryAndModelUpdateModel.getCode());
        model.setCode(code);
        model.setName(categoryAndModelUpdateModel.getName());
        this.modelRepository.save(model);
    }

    @Override
    public void delete(Long modelId) {
        Model model = this.modelRepository.findOne(modelId);
        if (model == null){
            throw new ModelNotFoundExeption();
        }

        this.modelRepository.delete(model);
    }


}
