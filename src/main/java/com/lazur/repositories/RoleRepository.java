package com.lazur.repositories;

import com.lazur.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{

    @Query(value = "SELECT r FROM Role AS r where r.authority = :roleName")
    Role findByAuthority(@Param("roleName") String roleName);
}

