package com.lazur.entities;


import com.lazur.entities.specific.SpecificMaterial;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "model_id")
    private Model model;

    @Column(length = 100000)
    private String image;

    @Column(length = 100000)
    private String description;

    private String barcodeEU;

    private String barcodeUS;

    @Column(nullable = true)
    private Float length;

    @Column(nullable = true)
    private Float width;

    @Column(nullable = true)
    private Float height;

    @Column(nullable = true)
    private Float weight;

    private String sku;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "family_id")
    private Family family;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "finish_id")
    private Material finish;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "frame_id")
    private Material frame;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "top_id")
    private Material top;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "specificMaterials_id")
    private SpecificMaterial specificMaterial;


    public Product() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public SpecificMaterial getSpecificMaterial() {
        return specificMaterial;
    }

    public void setSpecificMaterial(SpecificMaterial specificMaterial) {
        this.specificMaterial = specificMaterial;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getBarcodeEU() {
        return barcodeEU;
    }

    public void setBarcodeEU(String barcodeEU) {
        this.barcodeEU = barcodeEU;
    }

    public String getBarcodeUS() {
        return barcodeUS;
    }

    public void setBarcodeUS(String barcodeUS) {
        this.barcodeUS = barcodeUS;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Material getFinish() {
        return finish;
    }

    public void setFinish(Material finish) {
        this.finish = finish;
    }

    public Material getFrame() {
        return frame;
    }

    public void setFrame(Material frame) {
        this.frame = frame;
    }

    public Material getTop() {
        return top;
    }

    public void setTop(Material top) {
        this.top = top;
    }
}
