package com.lazur.repositories;

import com.lazur.entities.specific.SpecificProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SpecificProductRepository extends JpaRepository<SpecificProduct, Long>{

    SpecificProduct findByName(String productName);
}
