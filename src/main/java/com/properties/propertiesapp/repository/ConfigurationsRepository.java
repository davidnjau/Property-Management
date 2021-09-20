package com.properties.propertiesapp.repository;

import com.properties.propertiesapp.entity.Configurations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationsRepository extends JpaRepository<Configurations, String> {

    Configurations findAllByAdminEmailAddress(String adminEmailAddress);
}
