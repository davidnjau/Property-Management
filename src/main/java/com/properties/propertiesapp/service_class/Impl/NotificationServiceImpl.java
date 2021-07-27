package com.properties.propertiesapp.service_class.Impl;

import com.properties.propertiesapp.entity.Properties;
import com.properties.propertiesapp.entity.Receipts;
import com.properties.propertiesapp.helper_class.DbInformation;
import com.properties.propertiesapp.helper_class.NotificationDetails;
import com.properties.propertiesapp.service_class.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private PropertiesServiceImpl propertiesServiceImpl;

    @Autowired
    private ReceiptsServiceImpl receiptsServiceImpl;

    @Override
    public DbInformation dbInformation() {

        List<Receipts> receiptsList = receiptsServiceImpl.getAllReceipts();
        for (int i = 0; i < receiptsList.size(); i ++){

            String propertyId = receiptsList.get(i).getPropertyId();
            String referenceNo = receiptsList.get(i).getReceiptReference();
            Double amountPaid = receiptsList.get(i).getAmountPaid();
            Date datePaid = receiptsList.get(i).getDatePaid();

            Properties property = propertiesServiceImpl.getPropertyById(propertyId);
            if (property != null){

                String propertyName = property.getPropertyName();
                Double propertyIncAmount = property.getIncrementalPerc();
                Boolean isVat = property.isVat();
                Double rentAmount = property.getPropertyRentAmount();
                /**
                 * Check if rent amount is less than amount paid
                 */
                if (rentAmount.equals(amountPaid)){

                    String message = "Rent for property " +
                            propertyName
                            + " has been paid under receipt referenced " +
                            referenceNo ;

                    if (isVat)
                        message = message + " with a VAT of 16%.";

                    if (propertyIncAmount > 0)
                        message = message + " It has a incremental rent percentage of " + propertyIncAmount + " %.";


//                    NotificationDetails rentPaidnotificationDetails = new  NotificationDetails(
//                            "Completed",
//                            message,
//                            datePaid
//                    );


                }


            }

        }

        return null;
    }
}
