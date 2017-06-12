package com.lazur.repositories;

import com.lazur.entities.specific.ManufCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufCodeRepository  extends JpaRepository<ManufCode, Long>{

    ManufCode findByName(String manufCodeName);
}
