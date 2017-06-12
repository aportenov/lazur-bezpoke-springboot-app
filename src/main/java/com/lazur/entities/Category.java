package com.lazur.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<Model> models;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<Product> products;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Set<CategoryCode> categoryCodes;

    public Category() {
        this.categoryCodes = new LinkedHashSet<>();
        this.products = new LinkedHashSet<>();
        this.models = new LinkedHashSet<>();
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

    public Set<Product> getProducts() {
        return products;
    }

    public void addProducts(Product products) {
        this.getProducts().add(products);
    }


    public Set<CategoryCode> getCategoryCodes() {
        return categoryCodes;
    }

    public void addCategoryCodes(CategoryCode categoryCodes) {
        this.getCategoryCodes().add(categoryCodes);
    }

    public Set<Model> getModels() {
        return models;
    }

    public void setModels(Set<Model> models) {
        this.models = models;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
