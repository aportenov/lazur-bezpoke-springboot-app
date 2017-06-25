package com.lazur.models.categories;

import java.util.ArrayList;
import java.util.List;

public class CategoryEditViewModel {

    private Long id;

    private String name;

    private List<String> code;

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

    public List<String> getCode() {
        if (this.code == null){
            this.code = new ArrayList<>();
        }

        return code;
    }

    public void addCode(String code) {
        this.getCode().add(code);
    }
}

