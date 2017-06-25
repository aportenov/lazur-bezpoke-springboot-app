package com.lazur.models.models;


import com.lazur.models.families.FamilyViewModel;

import java.util.List;

public class ModelViewModel {

    private Long id;

    private String name;

    private String code;

    private List<FamilyViewModel> families;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<FamilyViewModel> getFamilies() {
        return families;
    }

    public void setFamilies(List<FamilyViewModel> families) {
        this.families = families;
    }
}
