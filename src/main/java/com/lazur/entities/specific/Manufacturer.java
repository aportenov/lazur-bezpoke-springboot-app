package com.lazur.entities.specific;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "manufacturers")
public class Manufacturer implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "manufacturer")
    private List<SpecificMaterial> specificMaterials;

    public Manufacturer() {
        this.specificMaterials = new ArrayList<>();
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

    public List<SpecificMaterial> getSpecificMaterials() {
        return specificMaterials;
    }

    public void setSpecificMaterials(List<SpecificMaterial> specificMaterials) {
        this.specificMaterials = specificMaterials;
    }
}
