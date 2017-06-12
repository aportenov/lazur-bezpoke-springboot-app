package com.lazur.controllers;

import com.lazur.models.view.*;
import com.lazur.services.CategoryService;
import com.lazur.services.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FamilyService familyService;


    @GetMapping("/categories")
    public String getEditCategoryPage(Model model){
        addCategoryToDropDown(model);
        if (! model.containsAttribute("category")){
            model.addAttribute("category", new CategoryBindingModel());
        }

        return "/categories/categories";
    }

    @PostMapping("/category/add")
    public String addCategory(@ModelAttribute CategoryBindingModel categoryBindingModel){
        this.categoryService.save(categoryBindingModel);

        return "redirect:/categories";
    }

    @GetMapping("/categories/{category}")
    public String getByCategoryPage(@PathVariable("category") String category, Model model){
        addCategoryToTable(category, model);
        addCategoryToDropDown(model);
        model.addAttribute("type", category);

        return "/categories/models";
    }

    @GetMapping("/categories/{category}/{modelName}")
    public String getModelsByCategoryPage(@PathVariable("modelName")String modelName,
                                        @PathVariable("category") String category,
                                        Model model) {
        addCategoryToDropDown(model);
        addCategoryToTable(category, model);
        model.addAttribute("currCategory", category);
        model.addAttribute("type", modelName);
        if (!model.containsAttribute("category")) {
            model.addAttribute("currModel", new ModelViewModel());
        }

        return "/categories/families";
    }


    @GetMapping("/categories/{category}/{modelName}/{family}")
    public String getFamiliesByModelsAndCategoryPage(@PathVariable("modelName")String modelName,
                                        @PathVariable("category") String category,
                                        @PathVariable("family") String family,
                                        Model model){
        addCategoryToDropDown(model);
        addCategoryToTable(category, model);
        model.addAttribute("familyName", family);
        model.addAttribute("currCategory", category);
        model.addAttribute("type", modelName);

        return "/categories/products";
    }

    @GetMapping("/categories/edit/{category}")
    public String getEditCategoryPage(@PathVariable("category") String category,
                                      Model model){
        CategoryEditViewModel categoryEditViewModel = this.categoryService.findCategoryByName(category);
        addCategoryToDropDown(model);
        addCategoryToTable(category, model);
        model.addAttribute("type", category);
        model.addAttribute("currCategory", categoryEditViewModel);

        return "/categories/categories-edit";
    }

    @PostMapping("/categories/update/{id}")
    public String updateCategoryPage(@PathVariable("id") Long id,
                                     @ModelAttribute CategoryAndModelUpdateModel categoryUpdateModel,
                                     Model model){
        this.categoryService.update(id,categoryUpdateModel);

        return "redirect:/categories";
    }

    @GetMapping("/categories/delete/{category}")
    public String getDeleteCategoryPage(@PathVariable("category") String category,
                                      Model model){
        CategoryEditModel categoryEditModel = this.categoryService.findDeleteCategoryByName(category);
        addCategoryToDropDown(model);
        addCategoryToTable(category, model);
        model.addAttribute("type", category);
        model.addAttribute("currCategory", categoryEditModel);

        return "/categories/categories-delete";
    }

    @PostMapping("/categories/delete/{id}")
    public String deleteCategoryPage(@PathVariable("id") Long id,
                                     Model model){
        this.categoryService.delete(id);

        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{category}/{modelName}/{id}")
    public String getEditCategoryModelPage(@PathVariable("category") String category,
                                      @PathVariable("modelName") String modelName,
                                      @PathVariable("id") Long id,
                                       Model model){
        ModelEditModel categoryEditModel = this.categoryService.findByModel(id);
        addCategoryToDropDown(model);
        addCategoryToTable(category, model);
        model.addAttribute("type", modelName);
        model.addAttribute("currModel", categoryEditModel);

        return "/categories/models-edit";
    }

    @GetMapping("/categories/edit/{category}/{modelName}/{familyName}/{id}")
    public String getEditCategoryFamilyPage(@PathVariable("category") String category,
                                           @PathVariable("modelName") String modelName,
                                           @PathVariable("familyName") String familyName,
                                           @PathVariable("id") Long id,
                                           Model model){
        FamilyViewModel familyViewModel = this.familyService.findByFamily(id);
        addCategoryToDropDown(model);
        addCategoryToTable(category, model);
        model.addAttribute("type", familyName);
        model.addAttribute("familyType", modelName);
        model.addAttribute("currFamily", familyViewModel);

        return "/categories/families-edit";
    }


    private void addCategoryToDropDown(Model model) {
        List<CategoryViewModel> categoryList = this.categoryService.getCategories();
        model.addAttribute("categories", categoryList);
    }


    private void addCategoryToTable(String category, Model model) {
        CategoryViewModel categoryViewModel = this.categoryService.findAllModelsByCategory(category);
        model.addAttribute("category", categoryViewModel);
    }



}
