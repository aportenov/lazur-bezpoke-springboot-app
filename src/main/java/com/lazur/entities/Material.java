package com.lazur.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

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
    private Set<Type> types;

    private String material;

    private String abbreviation;

    @OneToMany(mappedBy = "finish")
    private Set<Product> productFinish;

    @OneToMany(mappedBy = "frame")
    private Set<Product> productFrame;

    @OneToMany(mappedBy = "top")
    private Set<Product> productTop;

    public Material() {
        this.productFinish = new HashSet<>();
        this.productFrame = new HashSet<>();
        this.types = new LinkedHashSet<>();
        this.productTop = new HashSet<>();
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

    public Set<Type> getTypes() {
        return types;
    }


    public Set<Product> getProductFinish() {
        return productFinish;
    }

    public void setProductFinish(Set<Product> productFinish) {
        this.productFinish = productFinish;
    }

    public Set<Product> getProductFrame() {
        return productFrame;
    }

    public void setProductFrame(Set<Product> productFrame) {
        this.productFrame = productFrame;
    }

    public Set<Product> getProductTop() {
        return productTop;
    }

    public void setProductTop(Set<Product> productTop) {
        this.productTop = productTop;
    }
}
