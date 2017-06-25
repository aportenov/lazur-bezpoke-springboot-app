package com.lazur.models.materials;


import java.util.List;

public class MaterialViewModel {

    private Long id;

    private String material;

    private String abbreviation;

    private List<TypeViewModel> types;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<TypeViewModel> getTypes() {
        return types;
    }

    public void setTypes(List<TypeViewModel> types) {
        this.types = types;
    }
}
