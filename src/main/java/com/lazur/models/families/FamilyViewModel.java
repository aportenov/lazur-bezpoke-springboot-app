package com.lazur.models.families;

import com.lazur.models.products.ProductViewFamilyModel;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

public class FamilyViewModel {

    private Long id;

    @NotBlank(message = "Family Name cannot be empty")
    private String name;

    @NotNull(message = "Family Code cannot be empty")
    private Long code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private List<ProductViewFamilyModel> products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public List<ProductViewFamilyModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductViewFamilyModel> products) {
        this.products = products;
    }
}
