package com.lazur.models.categories;


import com.lazur.validations.IsCodeHasAvailableNumbers;
import org.hibernate.validator.constraints.NotBlank;


public class CategoryBindingModel {

    @NotBlank(message = "field Name cannot be empty")
    private String name;

    @NotBlank(message = "You need to add a Code for the Category")
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code.toUpperCase();
    }
}
