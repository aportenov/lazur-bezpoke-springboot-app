package com.lazur.repositories;

import com.lazur.entities.specific.SpecificMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecificMaterialRepository extends JpaRepository<SpecificMaterial, Long>{

    @Query("SELECT s FROM SpecificMaterial AS s " +
            "LEFT JOIN s.specificProduct AS sp " +
            "LEFT JOIN s.manufacturer AS m " +
            "LEFT JOIN s.manufCode AS mc " +
            "GROUP BY s " +
            "ORDER BY sp.name ASC, m.name ASC, mc.name ASC")
    List<SpecificMaterial> findAllMaterials();
}
