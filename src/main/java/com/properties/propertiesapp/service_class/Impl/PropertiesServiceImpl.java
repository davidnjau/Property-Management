package com.properties.propertiesapp.service_class.Impl;

import com.properties.propertiesapp.entity.Properties;
import com.properties.propertiesapp.helper_class.*;
import com.properties.propertiesapp.repository.PropertiesRepository;
import com.properties.propertiesapp.service_class.service.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PropertiesServiceImpl implements PropertiesService {

    @Autowired
    private PropertiesRepository propertiesRepository;

    @Autowired
    private ReceiptsServiceImpl receiptsService;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private ExpensesServiceImpl expensesService;

    @Autowired
    public ConfigurationsServiceImpl configurationsServiceImpl;

    @Override
    public Results addProperty(Properties properties) {

        /**
         * Check if admin address exists
         */

        try{

            Properties addedProperty = propertiesRepository.save(properties);

            String adminEmailAddress = configurationsServiceImpl.getAdminEmailAddress();
            if (adminEmailAddress != null){
                DataFormatter dataFormatter = new DataFormatter();
                dataFormatter.sendPropertyMail(emailService,addedProperty);

                return new Results(200, addedProperty);
            }else {
                return new Results(400, "Property has been saved but email has not been sent. Go to configurations and add an admin email address.");
            }



        }catch (Exception e){

            Boolean isPropertyName = checkPropertyByName(properties.getPropertyName());
            if (isPropertyName)
                return new Results(400, "Please select a different property name.");

            return new Results(400, "There was an issue adding the property.");

        }


    }

    public Properties updateProperty(Properties properties,String propertyId){

        Optional<Properties> optionalProperties = propertiesRepository.findById(propertyId);
        if (optionalProperties.isPresent()){

            Properties oldProperties = optionalProperties.get();


            Date occupancyDate = properties.getPropertyOccupancyDate();
            Date saveOccupancyDate;
            if (occupancyDate != null){
                saveOccupancyDate = occupancyDate;
            }else {
                saveOccupancyDate = oldProperties.getPropertyOccupancyDate();
            }

            oldProperties.setPropertyName(properties.getPropertyName());
            oldProperties.setPropertyDetails(properties.getPropertyName());
            oldProperties.setPropertyLocation(properties.getPropertyName());
            oldProperties.setPropertyLandlordDetails(properties.getPropertyName());
            oldProperties.setPaymentSchedule(properties.getPropertyName());
            oldProperties.setPropertyRentAmount(properties.getPropertyRentAmount());
            oldProperties.setIncrementalPerc(properties.getIncrementalPerc());
            oldProperties.setPropertyDepositAmount(properties.getPropertyDepositAmount());
            oldProperties.setVat(properties.isVat());

            oldProperties.setPropertyTenancyPeriod(properties.getPropertyTenancyPeriod());
            oldProperties.setPropertyOccupancyDate(saveOccupancyDate);

            return propertiesRepository.save(oldProperties);
        }else {

            Results results = addProperty(properties);
            var statusCode = results.getStatusCode();
            if (statusCode == 200) {
                return properties;
            }else {
                return null;
            }

        }


    }

    @Override
    public List<Properties> getAllProperty() {
        return findPaginatedProperties();
//        return propertiesRepository.findAll(Sort.by(Sort.Direction.ASC, "propertyName"));
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

    @Override
    public void deleteProperty(String propertyId) {
        receiptsService.deletePropertyReceipt(propertyId);
        expensesService.deletePropertyExpense(propertyId);
        propertiesRepository.deleteById(propertyId);
    }

    /**
     * MODEL FUNCTIONALITY
     */
    public DbResults1 getAllPropertyData(){

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");

            List<DbPropetiesData> dbPropetiesDataList = new ArrayList<>();
            List<Properties> propertyList = getAllProperty();

            for (int i = 0; i < propertyList.size(); i++){

                String id = propertyList.get(i).getId();
                String propertyOccupancyDate = sdf.format(propertyList.get(i).getPropertyOccupancyDate());

                boolean isVat = propertyList.get(i).isVat();
                String propertyVatStatus = "";
                if (isVat){
                    propertyVatStatus = "The property has a 16 % VAT.";
                }else {
                    propertyVatStatus = "The property does not have any VAT.";
                }
                String propertyName = propertyList.get(i).getPropertyName();
                String propertyLocation = propertyList.get(i).getPropertyLocation();
                String propertyDetails = propertyList.get(i).getPropertyDetails();
                String propertyLandlordDetails = propertyList.get(i).getPropertyLandlordDetails();
                String paymentSchedule = propertyList.get(i).getPaymentSchedule();
                String propertyTenancyPeriod = propertyList.get(i).getPropertyTenancyPeriod() + " year(s)";
                Double propertyRentAmount = propertyList.get(i).getPropertyRentAmount();
                String rentAmount = propertyList.get(i).getPropertyRentAmount() + " Kshs";
                Double incrementalPerc = propertyList.get(i).getIncrementalPerc();
                String incrementalData = "";
                if (incrementalPerc > 0){
                    incrementalData = "The property has an incremental percentage of " + incrementalPerc + " %. p.a.";
                }else {
                    incrementalData = "The property does not have an incremental percentage";
                }
                String propertyDepositAmount = propertyList.get(i).getPropertyDepositAmount() + " Kshs";

                DbPropetiesData dbPropetiesData = new DbPropetiesData(
                        id, propertyOccupancyDate, propertyVatStatus, propertyName, propertyLocation, propertyDetails,
                        propertyLandlordDetails, paymentSchedule, propertyTenancyPeriod, rentAmount,propertyRentAmount,
                        incrementalData, propertyDepositAmount);
                dbPropetiesDataList.add(dbPropetiesData);

            }

            return new DbResults1(
                    dbPropetiesDataList.size(),
                    null,
                    null,
                    dbPropetiesDataList
            );

        }catch (Exception e){
            return null;
        }

    }

    public DbPropetiesData getAllPropertyDetails(String propertyId){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");

        Properties properties = getPropertyById(propertyId);
        if (properties != null){

            String id = properties.getId();
            String propertyOccupancyDate = sdf.format(properties.getPropertyOccupancyDate());

            boolean isVat = properties.isVat();
            String propertyVatStatus = "";
            if (isVat){
                propertyVatStatus = "The property has a 16 % VAT.";
            }else {
                propertyVatStatus = "The property does not have any VAT.";
            }
            String propertyName = properties.getPropertyName();
            String propertyLocation = properties.getPropertyLocation();
            String propertyDetails = properties.getPropertyDetails();
            String propertyLandlordDetails = properties.getPropertyLandlordDetails();
            String paymentSchedule = properties.getPaymentSchedule();
            String propertyTenancyPeriod = properties.getPropertyTenancyPeriod() + " year(s)";
            Double propertyRentAmount = properties.getPropertyRentAmount();
            String rentAmount = properties.getPropertyRentAmount() + " Kshs";
            Double incrementalPerc = properties.getIncrementalPerc();
            String incrementalData = "";
            if (incrementalPerc > 0){
                incrementalData = "The property has an incremental percentage of " + incrementalPerc + " %. p.a.";
            }else {
                incrementalData = "The property does not have an incremental percentage";
            }
            String propertyDepositAmount = properties.getPropertyDepositAmount() + " Kshs";

            return new DbPropetiesData(
                    id, propertyOccupancyDate, propertyVatStatus, propertyName, propertyLocation, propertyDetails,
                    propertyLandlordDetails, paymentSchedule, propertyTenancyPeriod, rentAmount,propertyRentAmount,
                    incrementalData, propertyDepositAmount);

        }else {

            return null;

        }

    }

    public Properties getAllPropertyDataPropertyDetail(String propertyId){
        return getPropertyById(propertyId);
    }

    public List<Properties> findPaginatedProperties(){

        int pageSize = 10;
        String sortPageField = "";
        sortPageField = "createdAt";
        Sort sort = Sort.by(sortPageField).descending();
        Pageable pageable = PageRequest.of(0, pageSize, sort);
        Page<Properties> productsPage = propertiesRepository.findAll(pageable);
        return productsPage.getContent();

    }

}
