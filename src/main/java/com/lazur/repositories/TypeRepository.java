package com.lazur.repositories;

import com.lazur.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long>{

    @Query("SELECT t FROM Type AS t LEFT JOIN t.materials AS m WHERE m.material = :material")
    List<Type> findAllWhereMaterialIs(@Param("material") String material);
}
