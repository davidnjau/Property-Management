package com.properties.propertiesapp.repository;

import com.properties.propertiesapp.entity.Receipts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptsRepository extends JpaRepository<Receipts, String> {
    List<Receipts> findAllByPropertyId(String propertyId);

}