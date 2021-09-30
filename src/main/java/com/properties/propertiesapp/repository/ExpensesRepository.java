package com.properties.propertiesapp.repository;

import com.properties.propertiesapp.entity.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpensesRepository extends JpaRepository<Expenses, String> {
    List<Expenses> findAllByPropertyId(String propertyId);
    void deleteByPropertyId(String propertyId);

}