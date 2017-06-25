package com.lazur.models.materials;


public class SpecificMaterialViewBasicModel {

    private long id;

    private String specificProductName;

    private String colorName;

    private String manufacturerName;

    private String manufCodeName;

    private String code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSpecificProductName() {
        return specificProductName;
    }

    public void setSpecificProductName(String specificProductName) {
        this.specificProductName = specificProductName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getManufCodeName() {
        return manufCodeName;
    }

    public void setManufCodeName(String manufCodeName) {
        this.manufCodeName = manufCodeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
