package com.lazur.entities.materials;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "frame")
public class Frame extends Material{

    public Frame() {
    }
}
