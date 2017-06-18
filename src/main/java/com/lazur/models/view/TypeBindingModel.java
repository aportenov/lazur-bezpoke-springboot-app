package com.lazur.models.view;

import org.hibernate.validator.constraints.NotBlank;

public class TypeBindingModel {

    @NotBlank(message = "Material name cannot be empty")
    private String name;

    @NotBlank(message = "Type name cannot be empty")
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
