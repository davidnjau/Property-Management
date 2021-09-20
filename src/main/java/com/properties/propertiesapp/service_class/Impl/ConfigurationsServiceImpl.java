package com.properties.propertiesapp.service_class.Impl;

import com.properties.propertiesapp.entity.Configurations;
import com.properties.propertiesapp.repository.ConfigurationsRepository;
import com.properties.propertiesapp.service_class.service.ConfigurationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationsServiceImpl implements ConfigurationsService {

    @Autowired
    private ConfigurationsRepository configurationsRepository;

    @Override
    public Configurations addConfigurations(Configurations configurations) {
        return configurationsRepository.save(configurations);
    }

    @Override
    public List<Configurations> getConfigurations() {
        return configurationsRepository.findAll();
    }

    @Override
    public Configurations getConfigurationsById(String Id) {
        return configurationsRepository.getById(Id);
    }

    public String getAdminEmailAddress(){

        List<Configurations> configurationsList = getConfigurations();
        return configurationsList.get(0).getAdminEmailAddress();

    }


}
