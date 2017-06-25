package com.lazur.models.models;

import java.util.ArrayList;
import java.util.List;

public class ModelEditModel {

    private long modelId;

    private String modelName;

    private String modelCode;

    private String categoryCode;

    private List<String> code;

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
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
