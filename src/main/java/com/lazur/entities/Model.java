package com.lazur.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "models")
public class Model implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "model", fetch = FetchType.LAZY)
    private List<Product> products;

    @OneToMany(mappedBy = "model", fetch = FetchType.LAZY)
    @OrderBy("code ASC")
    private List<Family> families;

    public Model() {
        this.products = new ArrayList<>();
        this.families = new ArrayList<>();
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProducts(Product products) {
        this.getProducts().add(products);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Family> getFamilies() {
        return families;
    }

    public void addFamilies(Family families) {
        this.getFamilies().add(families);
    }
}
