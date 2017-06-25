package com.lazur.controllers;

import com.lazur.models.categories.*;
import com.lazur.models.families.FamilyBidnignModel;
import com.lazur.models.families.FamilyViewModel;
import com.lazur.models.models.ModelBindingModel;
import com.lazur.models.models.ModelEditModel;
import com.lazur.models.view.*;
import com.lazur.services.CategoryService;
import com.lazur.services.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {

    private static final String CATEGORIES = "categories";
    private static final String CATEGORY = "category";
    private static final String TYPE = "type";
    private static final String ID = "id";
    private static final String MODEL_NAME = "modelName";
    private static final String FAMILY_NAME = "familyName";
    private static final String CURR_MODEL = "currModel";
    private static final String CURR_CATEGORY = "currCategory";
    private static final String FAMILY = "family";
    private static final String CURR_FAMILY = "currFamily";
    private static final String FAMILY_TYPE = "familyType";
    private static final String CATEGORY_CODES = "categoryCodes";
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FamilyService familyService;


    @GetMapping("/categories")
    public String getEditCategoryPage(Model model) {
        addCategoryToDropDown(model);
        if (!model.containsAttribute(CATEGORY)) {
            model.addAttribute(CATEGORY, new CategoryBindingModel());
        }

        return "/categories/categories";
    }

    @PostMapping("/category/add")
    public String addCategory(@Valid @ModelAttribute CategoryBindingModel categoryBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(CATEGORY, categoryBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", bindingResult);
            return "redirect:/categories";
        }

        this.categoryService.save(categoryBindingModel);
        return "redirect:/categories";
    }

    @GetMapping("/categories/{category}")
    public String getByCategoryPage(@PathVariable(CATEGORY) String category, Model model) {
        addCategoryToTable(category, model);
        addCategoryToDropDown(model);
        model.addAttribute(TYPE, category);
        if (!model.containsAttribute(CURR_MODEL)) {
            model.addAttribute(CURR_MODEL, new ModelBindingModel());
        }

        return "/categories/models";
    }

    @GetMapping("/categories/{category}/{modelName}")
    public String getModelsByCategoryPage(@PathVariable(MODEL_NAME) String modelName,
                                          @PathVariable(CATEGORY) String category,
                                          Model model) {
        addCategoryToDropDown(model);
        addCategoryToTable(category, model);
        model.addAttribute(CURR_CATEGORY, category);
        model.addAttribute(TYPE, modelName);
        if (!model.containsAttribute(FAMILY)) {
            model.addAttribute(FAMILY, new FamilyBidnignModel());
        }

        return "/categories/families";
    }


    @GetMapping("/categories/{category}/{modelName}/{family}")
    public String getFamiliesByModelsAndCategoryPage(@PathVariable(MODEL_NAME) String modelName,
                                                     @PathVariable(CATEGORY) String category,
                                                     @PathVariable(FAMILY) String family,
                                                     Model model) {
        addCategoryToDropDown(model);
        addCategoryToTable(category, model);
        model.addAttribute(FAMILY_NAME, family);
        model.addAttribute(CURR_CATEGORY, category);
        model.addAttribute(TYPE, modelName);

        return "/categories/products";
    }

    @GetMapping("/categories/edit/{category}")
    public String getEditCategoryPage(@PathVariable(CATEGORY) String category,
                                      Model model) {

        addCategoryToDropDown(model);
        addCategoryToTable(category, model);
        model.addAttribute(TYPE, category);
        CategoryEditViewModel categoryEditViewModel = this.categoryService.findCategoryByName(category);
        model.addAttribute(CATEGORY_CODES, categoryEditViewModel.getCode());
        if (! model.containsAttribute(CURR_CATEGORY)) {
            model.addAttribute(CURR_CATEGORY, categoryEditViewModel);
        }

        return "/categories/categories-edit";
    }

    @PostMapping("/categories/update/{type}/{id}")
    public String updateCategoryPage(@PathVariable(ID) Long id,
                                     @PathVariable("type") String categoryName,
                                     @Valid @ModelAttribute CategoryAndModelUpdateModel categoryUpdateModel,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            categoryUpdateModel.setId(id);
            redirectAttributes.addFlashAttribute(CURR_CATEGORY, categoryUpdateModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.currCategory", bindingResult);
            return String.format("redirect:/categories/edit/%s", categoryName);
        }

        this.categoryService.update(id, categoryUpdateModel);
        return "redirect:/categories";
    }

    @GetMapping("/categories/delete/{category}")
    public String getDeleteCategoryPage(@PathVariable(CATEGORY) String category,
                                        Model model) {
        CategoryEditModel categoryEditModel = this.categoryService.findDeleteCategoryByName(category);
        addCategoryToDropDown(model);
        addCategoryToTable(category, model);
        model.addAttribute(TYPE, category);
        model.addAttribute(CURR_CATEGORY, categoryEditModel);
        return "/categories/categories-delete";
    }

    @PostMapping("/categories/delete/{id}")
    public String deleteCategoryPage(@PathVariable(ID) Long id) {
        this.categoryService.delete(id);
        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{category}/{modelName}/{id}")
    public String getEditCategoryModelPage(@PathVariable(CATEGORY) String category,
                                           @PathVariable(MODEL_NAME) String modelName,
                                           @PathVariable(ID) Long id,
                                           Model model) {
        ModelEditModel categoryEditModel = this.categoryService.findByModel(id);
        addCategoryToDropDown(model);
        addCategoryToTable(category, model);
        model.addAttribute(TYPE, modelName);
        model.addAttribute(CURR_MODEL, categoryEditModel);
        return "/categories/models-edit";
    }

    @GetMapping("/categories/delete/{category}/{modelName}/{id}")
    public String getDeleteCategoryModelPage(@PathVariable(CATEGORY) String category,
                                             @PathVariable(MODEL_NAME) String modelName,
                                             @PathVariable(ID) Long id,
                                             Model model) {
        ModelEditModel categoryEditModel = this.categoryService.findByModel(id);
        addCategoryToDropDown(model);
        addCategoryToTable(category, model);
        model.addAttribute(TYPE, modelName);
        model.addAttribute(CURR_MODEL, categoryEditModel);
        return "/categories/models-delete";
    }

    @GetMapping("/categories/edit/{category}/{modelName}/{familyName}/{id}")
    public String getEditCategoryFamilyPage(@PathVariable(CATEGORY) String category,
                                            @PathVariable(MODEL_NAME) String modelName,
                                            @PathVariable(FAMILY_NAME) String familyName,
                                            @PathVariable(ID) Long id,
                                            Model model) {

        addCategoryToDropDown(model);
        addCategoryToTable(category, model);
        model.addAttribute(TYPE, familyName);
        model.addAttribute(FAMILY_TYPE, modelName);
        model.addAttribute(CURR_CATEGORY, category);
        if (!model.containsAttribute(CURR_FAMILY)) {
            FamilyViewModel familyViewModel = this.familyService.findByFamily(id);
            model.addAttribute(CURR_FAMILY, familyViewModel);
        }

        return "/categories/families-edit";
    }

    @GetMapping("/categories/delete/{category}/{modelName}/{familyName}/{id}")
    public String getDeleteCategoryFamilyPage(@PathVariable(CATEGORY) String category,
                                              @PathVariable(MODEL_NAME) String modelName,
                                              @PathVariable(FAMILY_NAME) String familyName,
                                              @PathVariable(ID) Long id,
                                              Model model) {
        FamilyViewModel familyViewModel = this.familyService.findByFamily(id);
        addCategoryToDropDown(model);
        addCategoryToTable(category, model);
        model.addAttribute(TYPE, familyName);
        model.addAttribute(FAMILY_TYPE, modelName);
        model.addAttribute(CURR_FAMILY, familyViewModel);
        return "/categories/families-delete";
    }


    private void addCategoryToDropDown(Model model) {
        List<CategoryViewModel> categoryList = this.categoryService.getCategories();
        model.addAttribute(CATEGORIES, categoryList);
    }


    private void addCategoryToTable(String category, Model model) {
        CategoryViewModel categoryViewModel = this.categoryService.findAllModelsByCategory(category);
        model.addAttribute(CATEGORY, categoryViewModel);
    }
}
