package com.lazur.models.materials;

import com.lazur.validations.IsCodeExist;
import org.hibernate.validator.constraints.NotBlank;

@IsCodeExist()
public class SpecificBindingModel {

    private static final String EMPTY = "";
    private static final int FIRST_LETTER = 0;

    private Long id;

    private String specificProductName;

    private String colorName;

    private String manufacturerName;

    private String manufCodeName;

    @NotBlank(message = "Code field should not be empty")
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        if (code != EMPTY) {
            code = code.replace(",", "");
            String[] splitedCode = code.split(EMPTY);
            String upperCharCode = splitedCode[FIRST_LETTER].toUpperCase();
            splitedCode[FIRST_LETTER] = upperCharCode;
            String newCode = "";
            for (String s : splitedCode) {
                newCode += s;
            }
            this.code = newCode;
        }else {
            this.code = code;
        }
    }
}
