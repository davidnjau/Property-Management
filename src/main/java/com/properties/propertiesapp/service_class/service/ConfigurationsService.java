package com.properties.propertiesapp.service_class.service;

import com.properties.propertiesapp.entity.Configurations;

import java.util.List;

public interface ConfigurationsService {

    Configurations addConfigurations(Configurations configurations);
    List<Configurations> getConfigurations();
    Configurations getConfigurationsById(String Id);

}
