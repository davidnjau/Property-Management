package com.properties.propertiesapp.service_class.Impl;

import com.properties.propertiesapp.entity.Properties;
import com.properties.propertiesapp.entity.Receipts;
import com.properties.propertiesapp.helper_class.*;
import com.properties.propertiesapp.repository.ReceiptsRepository;
import com.properties.propertiesapp.service_class.service.ReceiptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class ReceiptsServiceImpl implements ReceiptsService {

    @Autowired
    private ReceiptsRepository receiptsRepository;

    @Autowired
    private PropertiesServiceImpl propertiesServiceImpl;

    @Autowired
    private PropertiesServiceImpl propertiesService;

    @Autowired
    private EmailServiceImpl emailService;

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
    
    public Receipts updateReceipts(Receipts receipts){

        Optional<Receipts> optionalProperties = receiptsRepository.findById(receipts.getId());
        if (optionalProperties.isPresent()){

            Receipts oldReceipts = optionalProperties.get();
            oldReceipts.setReceiptReference(receipts.getReceiptReference());
            oldReceipts.setAmountPaid(receipts.getAmountPaid());
            oldReceipts.setDatePaid(receipts.getDatePaid());
            oldReceipts.setRentAmount(receipts.getRentAmount());

            return receiptsRepository.save(oldReceipts);
        }else {
            return null;
        }
        
    }

    /**
     * MODELS
     */
    
    public DbReceiptsData getReceiptDataDetails(String receipt_id){
        
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");

        Receipts receipts = getReceiptDetails(receipt_id);
        String id = receipts.getId();
        String propertyId = receipts.getPropertyId();
        Properties properties = propertiesService.getPropertyById(propertyId);

        String propertyName = properties.getPropertyName();
        String amountPaid = receipts.getAmountPaid() + " Kshs";
        String referenceNo = receipts.getReceiptReference();
        String datePaid = sdf.format(receipts.getDatePaid());

        return new DbReceiptsData(id, propertyName, referenceNo, amountPaid, datePaid);
        
    }
    
    public Receipts saveReceipt(DbReceipts receipt){

        Properties properties = propertiesServiceImpl.findPropertyByName(receipt.getPropertyName());
        String propertyId = properties.getId();
        Double rentAmount = properties.getPropertyRentAmount();

        Double amountPaid = receipt.getAmountPaid();
        String referenceNumber = receipt.getReceiptReference();
        Date datePaid = receipt.getDatePaid();

        Receipts receipts = new Receipts(
          propertyId,amountPaid, referenceNumber, rentAmount, datePaid
        );
        Receipts addedReceipts = addReceipt(receipts);
        if (addedReceipts != null){

            String propertyName = propertiesService.getPropertyById(propertyId).getPropertyName();
            DbReceiptsData dbReceiptsData = new DbReceiptsData("", propertyName, referenceNumber, rentAmount.toString(), datePaid.toString());

            DataFormatter dataFormatter = new DataFormatter();
            dataFormatter.sendReceiptMail(emailService,dbReceiptsData);

        }

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
                Double deficitAmount = rentAmount - rentPaid;

                String monthName = getMonthName(datePaid);
                if (monthName.equals("Jan")){
                    janRent = janRent + deficitAmount;
                }
                else if (monthName.equals("Feb")){
                    febRent = febRent + deficitAmount;
                }
                else if (monthName.equals("Mar")){
                    marRent = marRent + deficitAmount;
                }
                else if (monthName.equals("Apr")){
                    aprRent = aprRent + deficitAmount;
                }
                else if (monthName.equals("May")){
                    mayRent = mayRent + deficitAmount;
                }
                else if (monthName.equals("Jun")){
                    junRent = junRent + deficitAmount;
                }
                else if (monthName.equals("Jul")){
                    julyRent = julyRent + deficitAmount;
                }
                else if (monthName.equals("Aug")){
                    augRent = augRent + deficitAmount;
                }
                else if (monthName.equals("Sep")){
                    septRent = septRent + deficitAmount;
                }
                else if (monthName.equals("Oct")){
                    octRent = octRent + deficitAmount;
                }
                else if (monthName.equals("Nov")){
                    novRent = novRent + deficitAmount;
                }
                else if (monthName.equals("Dec")){
                    decRent = decRent + deficitAmount;
                }


            }


        }
        rentRemainingPaidList.addAll(Arrays.asList(janRent, febRent,marRent,aprRent, mayRent,
                junRent,julyRent,augRent,septRent,octRent,novRent,decRent));

        return rentRemainingPaidList;

    }

    public DbRentPaid getRentComparison(){

        List<Double> rentPaidList = getMonthlyRent();
        List<Double> deficitsList = getRentDeficits();

        DbRentPaid dbRentPaid = new DbRentPaid(rentPaidList, deficitsList);
        return dbRentPaid;

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