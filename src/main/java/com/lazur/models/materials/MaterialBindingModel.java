package com.lazur.models.materials;

import org.hibernate.validator.constraints.NotBlank;

public class MaterialBindingModel {

    @NotBlank(message = "Field should not be empty")
    private String material;

    @NotBlank(message = "Field should not be empty")
    private String abbreviation;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

}
