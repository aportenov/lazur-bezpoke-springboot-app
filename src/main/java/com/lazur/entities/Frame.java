package com.lazur.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue(value = "frame")
public class Frame extends Material{

    public Frame() {
    }
}
