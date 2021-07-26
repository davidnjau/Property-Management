package com.properties.propertiesapp.service_class.Impl;

import com.properties.propertiesapp.entity.Properties;
import com.properties.propertiesapp.helper_class.DbResults;
import com.properties.propertiesapp.helper_class.Results;
import com.properties.propertiesapp.repository.PropertiesRepository;
import com.properties.propertiesapp.service_class.service.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertiesServiceImpl implements PropertiesService {

    @Autowired
    private PropertiesRepository propertiesRepository;

    @Override
    public Results addProperty(Properties properties) {

        try{

            Properties addedProperty = propertiesRepository.save(properties);
            return new Results(200, addedProperty);

        }catch (Exception e){

            Boolean isPropertyName = checkPropertyByName(properties.getPropertyName());
            if (isPropertyName)
                return new Results(400, "Please select a different property name.");

            return new Results(400, "There was an issue adding the property.");

        }


    }

    @Override
    public List<Properties> getAllProperty() {
        return propertiesRepository.findAll(Sort.by(Sort.Direction.ASC, "propertyName"));
    }

    @Override
    public Properties getPropertyById(String id) {

        Optional<Properties> propertiesOptional = propertiesRepository.findById(id);
        return propertiesOptional.orElse(null);

    }

    @Override
    public Boolean checkPropertyByName(String propertyName) {
        return propertiesRepository.existsByPropertyName(propertyName);
    }

    @Override
    public Properties findPropertyByName(String propertyName) {
        return propertiesRepository.findAllByPropertyName(propertyName);
    }

    /**
     * MODEL FUNCTIONALITY
     */
    public DbResults getAllPropertyData(){

        try{

            List<Properties> propertiesList = getAllProperty();

            return new DbResults(
                    propertiesList.size(),
                    null,
                    null,
                    propertiesList
            );

        }catch (Exception e){
            return null;
        }

    }

    public Properties getAllPropertyDetails(String propertyId){

        Properties properties = getPropertyById(propertyId);
        if (properties != null){

            return properties;

        }else {

            return null;

        }

    }


}
