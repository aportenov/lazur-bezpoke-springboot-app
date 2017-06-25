package com.lazur.models.materials;

import org.hibernate.validator.constraints.NotBlank;

public class TypeBindingModel {

    @NotBlank(message = "Material name should not be empty")
    private String name;

    @NotBlank(message = "Type name should not be empty")
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
