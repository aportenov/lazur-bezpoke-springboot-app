package com.lazur.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "name", discriminatorType = DiscriminatorType.STRING)
public abstract class Material implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", insertable = false, updatable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.REMOVE, mappedBy = "materials")
    private List<Type> types;

    private String material;

    private String abbreviation;

    @OneToMany(mappedBy = "finish")
    private List<Product> productFinish;

    @OneToMany(mappedBy = "frame")
    private List<Product> productFrame;

    @OneToMany(mappedBy = "top")
    private List<Product> productTop;

    public Material() {
        this.productFinish = new ArrayList<>();
        this.productFrame = new ArrayList<>();
        this.types = new ArrayList<>();
        this.productTop = new ArrayList<>();
    }

    public Material(String material, String abbreviation) {
        this.material = material;
        this.abbreviation = abbreviation;
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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void addTypes(Type types) {
        this.getTypes().add(types);
    }

    public List<Product> getProductFinish() {
        return productFinish;
    }

    public void setProductFinish(List<Product> productFinish) {
        this.productFinish = productFinish;
    }

    public List<Product> getProductFrame() {
        return productFrame;
    }

    public void setProductFrame(List<Product> productFrame) {
        this.productFrame = productFrame;
    }

    public List<Product> getProductTop() {
        return productTop;
    }

    public void setProductTop(List<Product> productTop) {
        this.productTop = productTop;
    }
}
