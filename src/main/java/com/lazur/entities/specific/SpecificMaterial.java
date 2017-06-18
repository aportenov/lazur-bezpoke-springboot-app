package com.lazur.entities.specific;


import com.lazur.entities.Product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "specific_materials")
public class SpecificMaterial implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specificProduct_id")
    private SpecificProduct specificProduct;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manufCode_id")
    private ManufCode manufCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "specificMaterial")
    private List<Product> products;

    private String code;

    public SpecificMaterial() {
        this.products = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SpecificProduct getSpecificProduct() {
        return specificProduct;
    }

    public void setSpecificProduct(SpecificProduct specificProduct) {
        this.specificProduct = specificProduct;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ManufCode getManufCode() {
        return manufCode;
    }

    public void setManufCode(ManufCode manufCode) {
        this.manufCode = manufCode;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
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

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
