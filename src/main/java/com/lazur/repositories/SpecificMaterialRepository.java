package com.lazur.repositories;

import com.lazur.entities.specific.SpecificMaterial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT s FROM SpecificMaterial AS s " +
            "LEFT JOIN s.specificProduct AS sp " +
            "LEFT JOIN s.manufacturer AS m " +
            "LEFT JOIN s.manufCode AS mc " +
            "WHERE NOT s.code = :code " +
            "GROUP BY s " +
            "ORDER BY sp.name ASC, m.name ASC, mc.name ASC")
    Page<SpecificMaterial> findAll(@Param("code") String code, Pageable pageable);
}
