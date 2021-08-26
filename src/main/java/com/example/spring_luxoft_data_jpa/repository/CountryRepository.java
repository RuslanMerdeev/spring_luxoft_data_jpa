package com.example.spring_luxoft_data_jpa.repository;

import com.example.spring_luxoft_data_jpa.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Transactional
    @Query("SELECT e FROM Country e")
    List<Country> getCountryList();

    @Transactional
    @Query("SELECT e FROM Country e WHERE e.name like :name")
    List<Country> getCountryListStartWith(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("UPDATE Country e SET e.name = :name where e.codeName = :codeName")
    void updateCountryName(@Param("codeName") String codeName, @Param("name") String name);

    @Transactional
    @Query("SELECT e FROM Country e WHERE e.codeName = :codeName")
    Country getCountryByCodeName(@Param("codeName") String codeName);

    @Transactional
    @Query("SELECT e FROM Country e WHERE e.name = :name")
    Country getCountryByName(@Param("name") String name) throws CountryNotFoundException;
}
