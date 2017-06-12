package com.lazur.models.view;

import javax.validation.constraints.NotNull;

public class ProductBiningModel {

    private Long id;

    @NotNull(message = "Please specify category")
    private String categoryName;

    private String modelName;

    @NotNull(message = "Product Name may not be empty")
    private String name;

    private String familyName;

    private String image;

    private String finishMaterial;

    private String finishType;

    private String frameMaterial;

    private String frameType;

    private String topMaterial;

    private String topType;

    private Float length;

    private Float width;

    private Float height;

    private Float weight;

    private Long specificMaterial;

    private String barcodeEU;

    private String barcodeUS;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFinishMaterial() {
        return finishMaterial;
    }

    public void setFinishMaterial(String finishMaterial) {
        this.finishMaterial = finishMaterial;
    }

    public String getFinishType() {
        return finishType;
    }

    public void setFinishType(String finishType) {
        this.finishType = finishType;
    }

    public String getFrameMaterial() {
        return frameMaterial;
    }

    public void setFrameMaterial(String frameMaterial) {
        this.frameMaterial = frameMaterial;
    }

    public String getFrameType() {
        return frameType;
    }

    public void setFrameType(String frameType) {
        this.frameType = frameType;
    }

    public String getTopMaterial() {
        return topMaterial;
    }

    public void setTopMaterial(String topMaterial) {
        this.topMaterial = topMaterial;
    }

    public String getTopType() {
        return topType;
    }

    public void setTopType(String topType) {
        this.topType = topType;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Long getSpecificMaterial() {
        return specificMaterial;
    }

    public void setSpecificMaterial(Long specificMaterial) {
        this.specificMaterial = specificMaterial;
    }

    public String getBarcodeEU() {
        return barcodeEU;
    }

    public void setBarcodeEU(String barcodeEU) {
        this.barcodeEU = barcodeEU;
    }

    public String getBarcodeUS() {
        return barcodeUS;
    }

    public void setBarcodeUS(String barcodeUS) {
        this.barcodeUS = barcodeUS;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
