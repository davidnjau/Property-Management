package com.properties.propertiesapp.service_class.service;

import com.properties.propertiesapp.entity.Properties;
import com.properties.propertiesapp.helper_class.Results;

import java.util.List;

public interface PropertiesService {
    Results addProperty(Properties properties);
    List<Properties> getAllProperty();
    Properties getPropertyById(String id);
    Boolean checkPropertyByName(String propertyName);
    Properties findPropertyByName(String propertyName);
}
