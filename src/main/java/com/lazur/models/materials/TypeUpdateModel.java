package com.lazur.models.materials;

import org.hibernate.validator.constraints.NotBlank;

public class TypeUpdateModel {

    private Long id;

    @NotBlank(message = "Field should not be empty")
    private String name;

    @NotBlank(message = "Field should not be empty")
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
