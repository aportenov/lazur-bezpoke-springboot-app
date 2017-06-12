package com.lazur.repositories;

import com.lazur.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {

    @Query(value = "SELECT p FROM Product AS p " +
            "LEFT JOIN p.model AS m " +
            "LEFT JOIN p.category AS c " +
            "WHERE m.name = :modelName " +
            "AND c.name =:category " +
            "GROUP BY p " +
            "ORDER BY m.code ASC")
    Page<Product> findAllByModelAndCategory(@Param("modelName") String modelName, @Param("category") String category, Pageable pageable);

    @Query(value = "SELECT p FROM Product AS p " +
            "LEFT JOIN p.category AS c  " +
            "LEFT JOIN c.categoryCodes AS cc " +
            "WHERE c.name = :category " +
            "GROUP BY p " +
            "ORDER BY cc.code ASC")
    Page<Product> findAllByCategory(@Param("category") String category, Pageable pageable);


    @Query(value = "SELECT p FROM Product AS p " +
            "LEFT JOIN p.family AS f " +
            "LEFT JOIN p.model AS m " +
            "LEFT JOIN p.category AS c " +
            "WHERE f.name = :family " +
            "AND m.name = :modelName " +
            "AND c.name = :category")
    Page<Product> findAllByFamilyModelAndCategory(@Param("family") String family,
                                                  @Param("modelName") String modelName,
                                                  @Param("category") String category,
                                                  Pageable pageable);
}

