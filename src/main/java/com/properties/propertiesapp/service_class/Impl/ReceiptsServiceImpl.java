package com.properties.propertiesapp.service_class.Impl;

import com.properties.propertiesapp.entity.Properties;
import com.properties.propertiesapp.entity.Receipts;
import com.properties.propertiesapp.helper_class.DbPaymentReceipt;
import com.properties.propertiesapp.helper_class.DbReceipts;
import com.properties.propertiesapp.helper_class.DbRentPaid;
import com.properties.propertiesapp.repository.ReceiptsRepository;
import com.properties.propertiesapp.service_class.service.ReceiptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class ReceiptsServiceImpl implements ReceiptsService {

    @Autowired
    private ReceiptsRepository receiptsRepository;

    @Autowired
    private PropertiesServiceImpl propertiesServiceImpl;

    @Override
    public Receipts addReceipt(Receipts receipt) {
        return receiptsRepository.save(receipt);
    }

    @Override
    public List<Receipts> getAllReceipts() {
        return receiptsRepository.findAll();
    }

    @Override
    public Receipts getReceiptDetails(String id) {

        Optional<Receipts> optionalReceipts = receiptsRepository.findById(id);
        return optionalReceipts.orElse(null);

    }

    @Override
    public Receipts getReceiptDetailsByPropertyId(String propertyId) {
        return receiptsRepository.findAllByPropertyId(propertyId);
    }

    /**
     * MODELS
     */
    public Receipts saveReceipt(DbReceipts receipt){

        Properties properties = propertiesServiceImpl.findPropertyByName(receipt.getPropertyName());
        String propertyId = properties.getId();

        Double amountPaid = receipt.getAmountPaid();
        String referenceNumber = receipt.getReceiptReference();
        Double rentAmount = receipt.getRentAmount();
        Date datePaid = receipt.getDatePaid();

        Receipts receipts = new Receipts(
          propertyId,amountPaid, referenceNumber, rentAmount, datePaid
        );

        return addReceipt(receipts);
    }

    public DbPaymentReceipt getAllReceiptData(){

        List<Receipts> receiptsList = getAllReceipts();
        DbPaymentReceipt dbPaymentReceipt = new DbPaymentReceipt(
                receiptsList.size(),
                null, null,
                receiptsList
        );

        return dbPaymentReceipt;
    }

    public List<Double> getMonthlyRent(){

        double janRent = 0.0;
        double febRent = 0.0;
        double marRent = 0.0;
        double aprRent = 0.0;
        double mayRent = 0.0;
        double junRent = 0.0;
        double julyRent = 0.0;
        double augRent = 0.0;
        double septRent = 0.0;
        double octRent = 0.0;
        double novRent = 0.0;
        double decRent = 0.0;

        List<Double> rentPaidList = new ArrayList<>();
        List<String> monthList = new ArrayList<>();

        List<Receipts> receiptsList = getAllReceipts();
        for (int i = 0; i < receiptsList.size(); i++){

            Date datePaid = receiptsList.get(i).getDatePaid();
            double rentPaid = receiptsList.get(i).getAmountPaid();

            String monthName = getMonthName(datePaid);
            if (monthName.equals("Jan")){
                janRent = janRent + rentPaid;
            }
            else if (monthName.equals("Feb")){
                febRent = febRent + rentPaid;
            }
            else if (monthName.equals("Mar")){
                marRent = marRent + rentPaid;
            }
            else if (monthName.equals("Apr")){
                aprRent = aprRent + rentPaid;
            }
            else if (monthName.equals("May")){
                mayRent = mayRent + rentPaid;
            }
            else if (monthName.equals("Jun")){
                junRent = junRent + rentPaid;
            }
            else if (monthName.equals("Jul")){
                julyRent = julyRent + rentPaid;
            }
            else if (monthName.equals("Aug")){
                augRent = augRent + rentPaid;
            }
            else if (monthName.equals("Sep")){
                septRent = septRent + rentPaid;
            }
            else if (monthName.equals("Oct")){
                octRent = octRent + rentPaid;
            }
            else if (monthName.equals("Nov")){
                novRent = novRent + rentPaid;
            }
            else if (monthName.equals("Dec")){
                decRent = decRent + rentPaid;
            }


        }
        rentPaidList.addAll(Arrays.asList(janRent, febRent,marRent,aprRent, mayRent,
                junRent,julyRent,augRent,septRent,octRent,novRent,decRent));


        return rentPaidList;

    }

    public List<Double> getRentDeficits(){

        double janRent = 0.0;
        double febRent = 0.0;
        double marRent = 0.0;
        double aprRent = 0.0;
        double mayRent = 0.0;
        double junRent = 0.0;
        double julyRent = 0.0;
        double augRent = 0.0;
        double septRent = 0.0;
        double octRent = 0.0;
        double novRent = 0.0;
        double decRent = 0.0;
        List<Double> rentRemainingPaidList = new ArrayList<>();

        List<Receipts> receiptsList = getAllReceipts();
        for (int i =0; i< receiptsList.size(); i++){

            Double rentPaid = receiptsList.get(i).getAmountPaid();
            Double rentAmount = receiptsList.get(i).getRentAmount();

            if (rentPaid < rentAmount){
                Date datePaid = receiptsList.get(i).getDatePaid();

                String monthName = getMonthName(datePaid);
                if (monthName.equals("Jan")){
                    janRent = janRent + rentPaid;
                }
                else if (monthName.equals("Feb")){
                    febRent = febRent + rentPaid;
                }
                else if (monthName.equals("Mar")){
                    marRent = marRent + rentPaid;
                }
                else if (monthName.equals("Apr")){
                    aprRent = aprRent + rentPaid;
                }
                else if (monthName.equals("May")){
                    mayRent = mayRent + rentPaid;
                }
                else if (monthName.equals("Jun")){
                    junRent = junRent + rentPaid;
                }
                else if (monthName.equals("Jul")){
                    julyRent = julyRent + rentPaid;
                }
                else if (monthName.equals("Aug")){
                    augRent = augRent + rentPaid;
                }
                else if (monthName.equals("Sep")){
                    septRent = septRent + rentPaid;
                }
                else if (monthName.equals("Oct")){
                    octRent = octRent + rentPaid;
                }
                else if (monthName.equals("Nov")){
                    novRent = novRent + rentPaid;
                }
                else if (monthName.equals("Dec")){
                    decRent = decRent + rentPaid;
                }


            }


        }
        rentRemainingPaidList.addAll(Arrays.asList(janRent, febRent,marRent,aprRent, mayRent,
                junRent,julyRent,augRent,septRent,octRent,novRent,decRent));

        return rentRemainingPaidList;

    }



    private String getMonthName(Date datePaid){

        LocalDate localDate = datePaid.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int monthN = localDate.getMonthValue();
        String monthName = "";
        if (monthN == 1){
            monthName = "Jan";
        }else if (monthN == 2){
            monthName = "Feb";
        }else if (monthN == 3){
            monthName = "Mar";
        }else if (monthN == 4){
            monthName = "Apr";
        }else if (monthN == 5){
            monthName = "May";
        }else if (monthN == 6){
            monthName = "Jun";
        }else if (monthN == 7){
            monthName = "Jul";
        }else if (monthN == 8){
            monthName = "Aug";
        }else if (monthN == 9){
            monthName = "Sep";
        }else if (monthN == 10){
            monthName = "Oct";
        }else if (monthN == 11){
            monthName = "Nov";
        }else if (monthN == 12){
            monthName = "Dec";
        }else {
            monthName = "";
        }

        return monthName;
    }

}