package com.lazur.repositories;

import com.lazur.entities.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long>{

    @Query("SELECT f FROM Family AS f " +
            "LEFT JOIN f.model AS m  " +
            "LEFT JOIN m.category AS c " +
            "WHERE f.name = :name " +
            "AND m.name = :productModel " +
            "AND c.name =:productCategory")
    Family findByNameModelAndCategory(@Param("name")String name, @Param("productModel") String productModel, @Param("productCategory") String productCategory);


    @Query(value = "SELECT f FROM Family AS f " +
            "LEFT JOIN f.model AS m  " +
            "LEFT JOIN m.category AS c " +
            "WHERE c.name = :category AND m.name = :model " +
            "ORDER BY f.code ASC")
    List<Family> findAllWhereCategoryAndModelAre(@Param("category") String category,@Param("model") String model);
}
