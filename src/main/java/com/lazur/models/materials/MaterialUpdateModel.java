package com.lazur.models.materials;

import org.hibernate.validator.constraints.NotBlank;

public class MaterialUpdateModel {

    private Long id;

    private String name;

    @NotBlank(message = "Field should not be empty")
    private String material;

    @NotBlank(message = "Field should not be empty")
    private String abbreviation;

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
