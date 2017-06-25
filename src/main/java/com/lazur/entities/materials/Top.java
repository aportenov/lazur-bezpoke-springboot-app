package com.lazur.entities.materials;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue(value = "top")
public class Top extends Material {

    public Top() {
    }
}
