package com.company.repository;

import com.company.model.Profession;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ProfessionRepository extends JpaRepository<Profession, Integer> {


    @Transactional
    @Modifying
    @Query("DELETE FROM Profession p WHERE p.id=:id")
    int delete(@Param("id") int id);

}
