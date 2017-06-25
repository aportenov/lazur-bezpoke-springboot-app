package com.lazur.models.families;


import org.hibernate.validator.constraints.NotBlank;

public class FamilyBidnignModel {

    @NotBlank(message =  "field Family cannot be empty")
    private String name;

    @NotBlank(message =  "No Model specified")
    private String type;

    @NotBlank(message =  "No Category specified")
    private String category;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
