package com.lazur.services;


import com.lazur.entities.Category;
import com.lazur.models.view.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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




