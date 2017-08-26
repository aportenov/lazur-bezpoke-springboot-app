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
public interface SpecificMaterialRepository extends JpaRepository<SpecificMaterial, Long> {

    @Query("SELECT s FROM SpecificMaterial AS s " +
            "LEFT JOIN s.specificProduct AS sp " +
            "LEFT JOIN s.manufacturer AS m " +
            "LEFT JOIN s.manufCode AS mc " +
            "LEFT JOIN s.color AS c " +
            "GROUP BY s " +
            "ORDER BY sp.name ASC, m.name ASC, mc.name ASC")
    List<SpecificMaterial> findAllMaterials();

    @Query("SELECT s FROM SpecificMaterial AS s " +
            "LEFT JOIN s.specificProduct AS sp " +
            "LEFT JOIN s.manufacturer AS m " +
            "LEFT JOIN s.manufCode AS mc " +
            "LEFT JOIN s.color AS c " +
            "WHERE NOT s.code = :code " +
            "GROUP BY s " +
            "ORDER BY sp.name ASC, m.name ASC, mc.name ASC")
    Page<SpecificMaterial> findAll(@Param("code") String code, Pageable pageable);

    @Query(value = "SELECT s FROM SpecificMaterial AS s " +
            "WHERE LOWER(s.code) = LOWER(:code) " +
            "AND NOT LOWER(s.id) = LOWER(:id)")
    SpecificMaterial findByCodeAndId(@Param("code") String code, @Param("id") Long id);

    @Query("SELECT s FROM SpecificMaterial AS s " +
            "LEFT JOIN s.specificProduct AS sp " +
            "LEFT JOIN s.manufacturer AS m " +
            "LEFT JOIN s.manufCode AS mc " +
            "LEFT JOIN s.color AS c " +
            "WHERE ( NOT s.id =:id " +
            "OR s.id IS NULL) " +
            "AND LOWER(c.name) = LOWER(:colorName) " +
            "AND LOWER(sp.name) = LOWER(:specificProductName) " +
            "AND LOWER(m.name) = LOWER(:manufacturerName) " +
            "AND LOWER(mc.name) = LOWER(:manufCodeName)")
    List<SpecificMaterial> findOneWithSpecification(
                                              @Param("id") Long id,
                                              @Param("specificProductName") String specificProductName,
                                              @Param("manufacturerName") String manufacturerName,
                                              @Param("colorName") String colorName,
                                              @Param("manufCodeName") String manufCodeName
    );

    @Query("SELECT s FROM SpecificMaterial AS s " +
            "LEFT JOIN s.specificProduct AS sp " +
            "LEFT JOIN s.manufacturer AS m " +
            "LEFT JOIN s.manufCode AS mc " +
            "LEFT JOIN s.color AS c " +
            "WHERE LOWER(c.name) = LOWER(:colorName) " +
            "AND LOWER(sp.name) = LOWER(:specificProductName) " +
            "AND LOWER(m.name) = LOWER(:manufacturerName) " +
            "AND LOWER(mc.name) = LOWER(:manufCodeName)")
    List<SpecificMaterial> findNewWithSpecification(
            @Param("specificProductName") String specificProductName,
            @Param("manufacturerName") String manufacturerName,
            @Param("colorName") String colorName,
            @Param("manufCodeName") String manufCodeName
    );
}
