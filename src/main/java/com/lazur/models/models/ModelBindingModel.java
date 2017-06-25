package com.lazur.models.models;

import com.lazur.validations.IsCodeHasAvailableNumbers;
import org.hibernate.validator.constraints.NotBlank;

@IsCodeHasAvailableNumbers
public class ModelBindingModel {

    @NotBlank(message =  "field Model should not be empty")
    private String name;

    @NotBlank(message = "no Category selected")
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
