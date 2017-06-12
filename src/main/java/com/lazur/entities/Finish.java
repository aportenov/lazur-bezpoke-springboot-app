package com.lazur.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue(value = "finish")
public class Finish extends Material{

    public Finish() {
    }
}
