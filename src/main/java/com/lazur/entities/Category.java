package com.lazur.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "categories")
public class Category implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Model> models;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    @OrderBy("id ASC")
    private List<CategoryCode> categoryCodes;

    public Category() {
        this.categoryCodes = new ArrayList<>();
        this.products = new ArrayList<>();
        this.models = new ArrayList<>();
    }

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

    public List<Product> getProducts() {
        return products;
    }

    public void addProducts(Product products) {
        this.getProducts().add(products);
    }


    public List<CategoryCode> getCategoryCodes() {
        return categoryCodes;
    }

    public void addCategoryCodes(CategoryCode categoryCodes) {
        this.getCategoryCodes().add(categoryCodes);
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
