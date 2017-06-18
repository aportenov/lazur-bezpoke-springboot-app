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

    @Query("SELECT c FROM Category AS c " +
            "LEFT JOIN c.categoryCodes AS cc " +
            "WHERE c.name =:name " +
            "ORDER BY cc.id ASC")
    Category findByName(@Param("name") String name);

    @Query("SELECT c FROM Category AS c " +
            "LEFT JOIN c.categoryCodes AS cc " +
            "LEFT JOIN c.models AS m " +
            "LEFT JOIN c.products AS p " +
            "LEFT JOIN m.families AS f " +
            "GROUP BY c " +
            "ORDER BY c.id ASC")
    List<Category> findAllCategories();

    @Query("SELECT c FROM Category AS c " +
            "LEFT JOIN c.categoryCodes AS cc " +
            "LEFT JOIN c.models AS m " +
            "LEFT JOIN m.families AS f " +
            "WHERE m.id =:id " +
            "ORDER BY cc.id")
    Category findCategoryByModelId(@Param("id") Long id);
}
