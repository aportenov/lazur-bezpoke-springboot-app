package com.lazur.repositories;

import com.lazur.entities.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.security.PermitAll;
import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model,Long>{

    @Query("SELECT m FROM Model AS m WHERE SUBSTRING(m.code, 1,1) IN :codes")
    List<Model> findAllByType(@Param("codes") List<String> codes);

    @Query("SELECT m FROM Model AS m " +
            "LEFT JOIN m.category AS c " +
            "WHERE c.name = :category " +
            "ORDER BY m.code ASC")
    List<Model> findAllByCategory(@Param("category") String category);

    @Query("SELECT m FROM Model AS m LEFT JOIN m.category AS c WHERE c.name = :category AND m.name =:model")
    Model findModelByCategoryAndModelName(@Param("category")String category, @Param("model") String model);
}
