package com.properties.propertiesapp.service_class.Impl;

import com.properties.propertiesapp.entity.Properties;
import com.properties.propertiesapp.entity.Receipts;
import com.properties.propertiesapp.helper_class.DbInformation;
import com.properties.propertiesapp.helper_class.DbNotifications;
import com.properties.propertiesapp.helper_class.Notification;
import com.properties.propertiesapp.helper_class.NotificationDetails;
import com.properties.propertiesapp.service_class.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private PropertiesServiceImpl propertiesServiceImpl;

    @Autowired
    private ReceiptsServiceImpl receiptsServiceImpl;

    @Override
    public DbInformation getNotices() {

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");

        List<NotificationDetails> rentPaidList = new ArrayList<>();
        List<NotificationDetails> overdueList = new ArrayList<>();
        List<NotificationDetails> notificationDetailsList = new ArrayList<>();

        List<Receipts> receiptsList = receiptsServiceImpl.getAllReceipts();
        for (int i = 0; i < receiptsList.size(); i ++){

            String propertyId = receiptsList.get(i).getPropertyId();
            String referenceNo = receiptsList.get(i).getReceiptReference();
            Double amountPaid = receiptsList.get(i).getAmountPaid();
            Date datePaid = receiptsList.get(i).getDatePaid();

            Properties property = propertiesServiceImpl.getPropertyById(propertyId);
            if (property != null){

                 notificationDetailsList =  getNotices(property);

                String propertyName = property.getPropertyName();
                Double propertyIncAmount = property.getIncrementalPerc();
                boolean isVat = property.isVat();
                Double rentAmount = property.getPropertyRentAmount();
                String strDate = sdf.format(datePaid);

                /**
                 * Check if rent amount is less than amount paid
                 */
                if (rentAmount.equals(amountPaid) || rentAmount > amountPaid){

                    String message = "Rent for property " +
                            propertyName
                            + " has been paid under receipt referenced " +
                            referenceNo ;

                    if (isVat)
                        message = message + " .It has a VAT of 16%.";

                    if (propertyIncAmount > 0)
                        message = message + " .It has a incremental rent percentage of " + propertyIncAmount + " %.";

                    String completed = Notification.COMPLETED.name();


                    NotificationDetails rentPaidNotificationDetails = new  NotificationDetails(
                            completed,
                            message,
                            strDate
                    );
                    rentPaidList.add(rentPaidNotificationDetails);

                }else if (rentAmount < amountPaid){

                    String message = "Rent for property " +
                            propertyName
                            + " has been paid under receipt referenced " +
                            referenceNo +
                            ". The rent is still not fully paid.";

                    if (isVat)
                        message = message + " It has a VAT of 16%.";

                    if (propertyIncAmount > 0)
                        message = message + " It has a incremental rent percentage of " + propertyIncAmount + " %.";

                    String underPaid = Notification.UNDERPAID.name();

                    NotificationDetails rentPaidNotificationDetails = new  NotificationDetails(
                            underPaid,
                            message,
                            strDate
                    );
                    overdueList.add(rentPaidNotificationDetails);

                }


                /**
                 * Checking upcoming rents
                 */



            }

        }

        DbNotifications dbNotifications = new DbNotifications(
                notificationDetailsList,
                rentPaidList,
                overdueList);


        return new DbInformation(
                notificationDetailsList.size(),
                rentPaidList.size(),
                overdueList.size(),
                dbNotifications
        );
    }

    private List<NotificationDetails> getNotices(Properties property){

        List<NotificationDetails> noticesList = new ArrayList<>();
        SimpleDateFormat daySdf = new SimpleDateFormat("EEE, MMM d, yyyy");


        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String paymentSchedule = property.getPaymentSchedule();
        String propertyName = property.getPropertyName();
        Double propertyRent = property.getPropertyRentAmount();
        Date date = property.getPropertyOccupancyDate();
        Double tenancyPeriod = property.getPropertyTenancyPeriod();
        int period = (int)Math.round(tenancyPeriod);

        /**
         * Check if tenancy has elapsed
         */
        c.setTime(date);
        c.add(Calendar.YEAR, period);
        Date date1 = c.getTime();

        String dateToElapse = sdf.format(date1);
        String todayDate = new SimpleDateFormat("yyyy-MM-dd")
                .format(Calendar.getInstance().getTime());

        long monthsBetween = ChronoUnit.MONTHS.between(
                LocalDate.parse(todayDate).withDayOfMonth(1),
                LocalDate.parse(dateToElapse).withDayOfMonth(1));

        if (monthsBetween > 1){

            if (monthsBetween < 5){
                String strDate = daySdf.format(todayDate);

                /**
                 * Create notification for termination of lease in 5 months
                 */
                String notification = Notification.NOTIFICATION.name();

                String message =
                        "Property " + propertyName + " lease is ending in "
                                + monthsBetween + " months.";

                NotificationDetails rentPaidNotificationDetails = new  NotificationDetails(
                        notification,
                        message,
                        strDate
                );

                noticesList.add(rentPaidNotificationDetails);

            }

            String message = "Property "+ propertyName + " rent of " + propertyRent + " Kshs. is due in " + paymentSchedule;

            Date date2 = new Date();
            String notification = Notification.NOTIFICATION.name();
            String strDate = daySdf.format(date2);

            NotificationDetails rentPaidNotificationDetails = new
                    NotificationDetails(
                    notification,
                    message,
                    strDate);

            noticesList.add(rentPaidNotificationDetails);

        }else {

            /**
             * Create notification for termination of lease in 1 month
             */
            String strDate = daySdf.format(todayDate);

            String notification = Notification.NOTIFICATION.name();

            String message =
                    "Property " + propertyName + " lease is ending in "
                            + monthsBetween + " months.";

            NotificationDetails rentPaidNotificationDetails = new
                    NotificationDetails(
                    notification,
                    message,
                    strDate);

            noticesList.add(rentPaidNotificationDetails);

        }

        return noticesList;


    }
}
