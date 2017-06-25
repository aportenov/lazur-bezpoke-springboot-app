package com.lazur.entities.materials;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "finish")
public class Finish extends Material{

    public Finish() {
    }
}
