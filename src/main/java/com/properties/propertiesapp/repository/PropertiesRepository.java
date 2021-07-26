package com.properties.propertiesapp.repository;

import com.properties.propertiesapp.entity.Properties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertiesRepository extends JpaRepository<Properties, String> {
    Boolean existsByPropertyName(String propertyName);
    Properties findAllByPropertyName(String name);
}