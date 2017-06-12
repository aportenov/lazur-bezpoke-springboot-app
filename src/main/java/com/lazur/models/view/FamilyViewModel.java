package com.lazur.models.view;

import java.util.List;

public class FamilyViewModel {

    private Long id;

    private String name;

    private Long code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private List<ProductViewFamilyModel> products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public List<ProductViewFamilyModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductViewFamilyModel> products) {
        this.products = products;
    }
}
