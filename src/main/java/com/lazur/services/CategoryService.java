package com.lazur.services;


import com.lazur.entities.Category;
import com.lazur.models.categories.*;
import com.lazur.models.models.ModelEditModel;

import java.util.List;

public interface CategoryService {

    List<CategoryViewModel> getCategories();

    void save(CategoryBindingModel categoryBindingModel);

    Category findCategory(String category);

    ModelEditModel findByModel(Long id);

    CategoryViewModel findAllModelsByCategory(String category);

    CategoryEditViewModel findCategoryByName(String category);

    void update(Long id, CategoryAndModelUpdateModel categoryEditModel);

    CategoryEditModel findDeleteCategoryByName(String category);

    void delete(Long id);
}




