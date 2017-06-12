package com.lazur.repositories;

import com.lazur.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category AS c WHERE c.name =:name")
    Category findByName(@Param("name") String name);

    @Query("SELECT c FROM Category AS c " +
            "LEFT JOIN c.categoryCodes AS cc " +
            "LEFT JOIN c.models AS m " +
            "LEFT JOIN c.products AS p " +
            "LEFT JOIN m.families AS f " +
            "GROUP BY c " +
            "ORDER BY  cc.code ASC, m.code ASC, f.code ASC")
    List<Category> findAllCategories();

    @Query("SELECT c FROM Category AS c " +
            "LEFT JOIN c.categoryCodes AS cc " +
            "LEFT JOIN c.models AS m " +
            "LEFT JOIN m.families AS f " +
            "WHERE m.id =:id")
    Category findCategoryByModelId(@Param("id") Long id);
}
