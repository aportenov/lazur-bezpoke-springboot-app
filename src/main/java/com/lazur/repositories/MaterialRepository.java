package com.lazur.repositories;

import com.lazur.entities.materials.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long>{

    @Query(value = "SELECT m FROM Material AS m " +
            "WHERE LOWER(m.name) = LOWER(:material) " +
            "ORDER BY m.id ASC")
    List<Material> findAllWhereNameIs(@Param("material") String material);

    @Query("SELECT m FROM Material AS m LEFT JOIN m.types AS t " +
            "WHERE LOWER(m.material) = LOWER(:material) AND LOWER(t.name) = LOWER(:type)")
    Material findByMaterialAndType(@Param("material") String material, @Param("type") String type);

    @Query("SELECT m FROM Material AS m " +
            "WHERE LOWER(m.material) = LOWER(:material) AND LOWER(m.name) = LOWER(:name)")
    Material findOneByMaterialAndName(@Param("material") String material,@Param("name") String name);

    @Query("SELECT m FROM Material AS m " +
            "LEFT JOIN m.types AS t " +
            "WHERE LOWER(m.name) = LOWER(:name)")
    Page<Material> findAllPageWhereMaterialIs(@Param("name") String name, Pageable pageable);

    @Query("SELECT m FROM Material AS m " +
            "LEFT JOIN m.types AS t " +
            "WHERE LOWER(m.name) = LOWER(:name) " +
            "AND LOWER(m.material) LIKE LOWER(:product)")
    Page<Material> findAllPageWhereMaterialAndTypeAre(@Param("name") String name, @Param("product") String product, Pageable pageable);

    @Query(value = "SELECT m FROM Material AS m " +
            "WHERE LOWER(m.name) = LOWER(:material) " +
            "OR LOWER(m.name) = LOWER(:none) " +
            "ORDER BY m.material ASC")
    List<Material> findAllWhereNameIsAndNone(@Param("material") String material, @Param("none") String none);
}
