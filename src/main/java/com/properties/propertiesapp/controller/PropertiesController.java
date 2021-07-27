package com.properties.propertiesapp.controller;

import com.properties.propertiesapp.entity.Properties;
import com.properties.propertiesapp.entity.Receipts;
import com.properties.propertiesapp.helper_class.*;
import com.properties.propertiesapp.service_class.Impl.PropertiesServiceImpl;
import com.properties.propertiesapp.service_class.Impl.ReceiptsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PropertiesController {

    @Autowired
    private PropertiesServiceImpl propertiesServiceImpl;

    @Autowired
    private ReceiptsServiceImpl receiptsServiceImpl;

    @RequestMapping(value = "/api/v1/properties/add_property", method = RequestMethod.POST)
    public ResponseEntity addProperty(@RequestBody Properties properties){

        Results addedProperties = propertiesServiceImpl.addProperty(properties);
        if (addedProperties != null){

            var statusCode = addedProperties.getStatusCode();
            var results = addedProperties.getDetails();
            if (statusCode == 200){
                return new ResponseEntity(results, HttpStatus.OK);
            }else {
                var results1 = results.toString();

                return ResponseEntity.badRequest().body(new ErrorMessage(results1));

            }



        }else {
            return ResponseEntity.internalServerError().body(new ErrorMessage("Please try again."));
        }
    }

    @RequestMapping(value = "/api/v1/properties/get_property/", method = RequestMethod.GET)
    public ResponseEntity getProperty(){

        DbResults getProperties = propertiesServiceImpl.getAllPropertyData();
        if (getProperties != null){

            return new ResponseEntity(getProperties, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("Please try again."));
        }
    }

    @RequestMapping(value = "/api/v1/properties/get_property/{property_id}", method = RequestMethod.GET)
    public ResponseEntity getPropertyDetails(@PathVariable("property_id") String property_id){

        Properties getProperties = propertiesServiceImpl.getAllPropertyDetails(property_id);
        if (getProperties != null){

            return new ResponseEntity(getProperties, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("There is no property associated with that id"));
        }
    }

    @RequestMapping(value = "/api/v1/receipts/add_receipts", method = RequestMethod.POST)
    public ResponseEntity addPaymentReceipt(@RequestBody DbReceipts receipts){
        Receipts addedReceipt = receiptsServiceImpl.saveReceipt(receipts);
        if (addedReceipt != null){

            return new ResponseEntity(addedReceipt, HttpStatus.OK);

        }else {
            return ResponseEntity.internalServerError().body(new ErrorMessage("Failed to add property. Please try again."));
        }
    }

    @RequestMapping(value = "/api/v1/receipts/get_receipts/", method = RequestMethod.GET)
    public ResponseEntity getPayementReceipts(){

        DbPaymentReceipt getProperties = receiptsServiceImpl.getAllReceiptData();
        if (getProperties != null){

            return new ResponseEntity(getProperties, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("Please try again."));
        }
    }

    @RequestMapping(value = "/api/v1/receipts/get_monthly_payments/", method = RequestMethod.GET)
    public ResponseEntity getMonthlyPayments(){

        List<Double> getProperties = receiptsServiceImpl.getMonthlyRent();
        if (getProperties != null){

            return new ResponseEntity(getProperties, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("Please try again."));
        }
    }
    @RequestMapping(value = "/api/v1/receipts/get_monthly_deficits/", method = RequestMethod.GET)
    public ResponseEntity getMonthlyDesficits(){

        List<Double> getProperties = receiptsServiceImpl.getRentDeficits();
        if (getProperties != null){

            return new ResponseEntity(getProperties, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("Please try again."));
        }
    }

    @RequestMapping(value = "/api/v1/receipts/get_monthly_comparison/", method = RequestMethod.GET)
    public ResponseEntity getMonthlyComparison(){

        DbRentPaid getProperties = receiptsServiceImpl.getRentComparison();
        if (getProperties != null){

            return new ResponseEntity(getProperties, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("Please try again."));
        }
    }


}
