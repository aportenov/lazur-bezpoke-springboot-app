package com.lazur.repositories;

import com.lazur.entities.Category;
import com.lazur.entities.CategoryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryCodeRepository extends JpaRepository<CategoryCode, Long>{

    @Query(value = "SELECT c FROM CategoryCode AS c LEFT JOIN c.category AS ct WHERE LOWER(ct.name) = LOWER(:name)")
    List<CategoryCode> findAllByCategory(@Param("name") String name);


    @Query("SELECT c FROM CategoryCode AS c " +
            "LEFT JOIN c.category AS ct " +
            "WHERE ct.id = :categoryId " +
            "AND LOWER(c.code) = LOWER(:code)")
    CategoryCode findByCategoryAndCode(@Param("categoryId")Long categoryId, @Param("code") String code);
}
