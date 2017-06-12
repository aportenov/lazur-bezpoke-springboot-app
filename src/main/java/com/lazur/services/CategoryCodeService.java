package com.lazur.services;

import com.lazur.entities.CategoryCode;

import java.util.List;

public interface CategoryCodeService {

    CategoryCode getCode(Long category, String code);

    List<CategoryCode> findAllByCategory(String name);

    void update(CategoryCode categoryCode);
}

