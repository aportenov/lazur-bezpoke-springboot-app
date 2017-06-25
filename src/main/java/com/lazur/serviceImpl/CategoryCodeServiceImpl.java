package com.lazur.serviceImpl;

import com.lazur.entities.CategoryCode;
import com.lazur.exeptions.CategoryCodeNotFoundExeption;
import com.lazur.repositories.CategoryCodeRepository;
import com.lazur.services.CategoryCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryCodeServiceImpl implements CategoryCodeService{

    private final CategoryCodeRepository categoryCodeRepository;

    @Autowired
    public CategoryCodeServiceImpl(CategoryCodeRepository categoryCodeRepository) {
        this.categoryCodeRepository = categoryCodeRepository;
    }

    @Override
    public CategoryCode getCode(Long categoryId, String code) {
       CategoryCode categoryCode = this.categoryCodeRepository.findByCategoryAndCode(categoryId,code);
       if (categoryCode == null){
           throw new CategoryCodeNotFoundExeption();
       }

       return categoryCode;
    }

    @Override
    public List<CategoryCode> findAllByCategory(String name) {
        return this.categoryCodeRepository.findAllByCategory(name);
    }

    @Override
    public void update(CategoryCode categoryCode) {
        this.categoryCodeRepository.save(categoryCode);
    }
}
