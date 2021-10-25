package com.properties.propertiesapp.controller;

import com.properties.propertiesapp.entity.Expenses;
import com.properties.propertiesapp.entity.Properties;
import com.properties.propertiesapp.entity.Receipts;
import com.properties.propertiesapp.helper_class.*;
import com.properties.propertiesapp.service_class.Impl.ExpensesServiceImpl;
import com.properties.propertiesapp.service_class.Impl.NotificationServiceImpl;
import com.properties.propertiesapp.service_class.Impl.PropertiesServiceImpl;
import com.properties.propertiesapp.service_class.Impl.ReceiptsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private PropertiesServiceImpl propertiesService;

    @Autowired
    private ReceiptsServiceImpl receiptsServiceImpl;

    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    @Autowired
    private ExpensesServiceImpl expensesServiceImpl;

    @Autowired
    private ReceiptsServiceImpl receiptsService;


    @RequestMapping(value = "/login")
    public String getLogin(){
        return "login";
    }
    @RequestMapping(value = "/register")
    public String getRegister(){
        return "register";
    }

    @RequestMapping(value = "/")
    public ModelAndView getDashboard() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");

        DbExpensesResults dbExpensesResults = expensesServiceImpl.getAllAvailableExpenses();
        List<Expenses> expensesList = dbExpensesResults.getResults();
        List<DbExpensesData> dbExpensesDataList = new ArrayList<>();

        for (int i = 0; i < expensesList.size(); i++) {

            String id = expensesList.get(i).getId();
            String propertyName = propertiesService.getPropertyById(
                    expensesList.get(i).getPropertyId()).getPropertyName();
            String expenseName = expensesList.get(i).getExpenseName();
            Double expenseAmount = expensesList.get(i).getExpenseAmount();
            Date expenseDate = expensesList.get(i).getExpenseDate();
            String expenseType = expensesList.get(i).getExpenseType();
            String strDate = sdf.format(expenseDate);
            String newAmount = expenseAmount + " Kshs";

            DbExpensesData dbExpensesData = new DbExpensesData(id, propertyName, expenseName, newAmount, strDate, expenseType);
            dbExpensesDataList.add(dbExpensesData);

        }

        DbResults1 dbResults = propertiesService.getAllPropertyData();
        List<String> propertyNameList = new ArrayList<>();

        List<DbPropetiesData> propertyList = dbResults.getResults();

        List<Receipts> receiptsList = receiptsServiceImpl.getAllReceipts();
        List<DbReceiptsData> dbReceiptsDataList = new ArrayList<>();
        for (int i = 0; i < receiptsList.size(); i++) {

            String id = receiptsList.get(i).getId();
            String propertyId = receiptsList.get(i).getPropertyId();
            Properties properties = propertiesService.getPropertyById(propertyId);
            String propertyName = properties.getPropertyName();

            String amountPaid = receiptsList.get(i).getAmountPaid() + " Kshs";
            String referenceNo = receiptsList.get(i).getReceiptReference();
            String datePaid = sdf.format(receiptsList.get(i).getDatePaid());

            DbReceiptsData dbReceiptsData = new DbReceiptsData(id, propertyName, referenceNo, amountPaid, datePaid);
            dbReceiptsDataList.add(dbReceiptsData);

        }


        DbInformation getNoticies = notificationServiceImpl.getNotices();

        List<NotificationDetails> noticeList = getNoticies.getNoticeBoard().getNotices();
        List<NotificationDetails> rentPaidList = getNoticies.getNoticeBoard().getRentPaid();
        List<NotificationDetails> overDueList = getNoticies.getNoticeBoard().getOverdueRent();


        for (DbPropetiesData properties : propertyList) {

            String propertyName = properties.getPropertyName();
            propertyNameList.add(propertyName);
        }

        List<DBReceiptProperty> dbReceiptPropertyList = receiptsServiceImpl.getPropertyReceipts();
        List<DbExpenseProperty> dbExpensePropertyList = expensesServiceImpl.getExpenseProperty();

        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("propertyList", propertyList);
        modelAndView.addObject("propertyNameList", propertyNameList);
        modelAndView.addObject("receiptsList", dbReceiptsDataList);
        modelAndView.addObject("noticeList", noticeList);
        modelAndView.addObject("rentPaidList", rentPaidList);
        modelAndView.addObject("dbReceiptPropertyList", dbReceiptPropertyList);
        modelAndView.addObject("dbExpensePropertyList", dbExpensePropertyList);
        modelAndView.addObject("overDueList", overDueList);
        modelAndView.addObject("expensesList", dbExpensesDataList);

        return modelAndView;
    }

    @RequestMapping(value = "/home")
    public ModelAndView goHome() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");

        DbExpensesResults dbExpensesResults = expensesServiceImpl.getAllAvailableExpenses();
        List<Expenses> expensesList = dbExpensesResults.getResults();
        List<DbExpensesData> dbExpensesDataList = new ArrayList<>();

        for (int i = 0; i < expensesList.size(); i++) {

            String id = expensesList.get(i).getId();
            String propertyName = propertiesService.getPropertyById(
                    expensesList.get(i).getPropertyId()).getPropertyName();
            String expenseName = expensesList.get(i).getExpenseName();
            Double expenseAmount = expensesList.get(i).getExpenseAmount();
            Date expenseDate = expensesList.get(i).getExpenseDate();
            String expenseType = expensesList.get(i).getExpenseType();
            String strDate = sdf.format(expenseDate);
            String newAmount = expenseAmount + " Kshs";

            DbExpensesData dbExpensesData = new DbExpensesData(id, propertyName, expenseName, newAmount, strDate, expenseType);
            dbExpensesDataList.add(dbExpensesData);

        }

        DbResults1 dbResults = propertiesService.getAllPropertyData();
        List<String> propertyNameList = new ArrayList<>();

        List<DbPropetiesData> propertyList = dbResults.getResults();

        List<Receipts> receiptsList = receiptsServiceImpl.getAllReceipts();
        List<DbReceiptsData> dbReceiptsDataList = new ArrayList<>();
        for (int i = 0; i < receiptsList.size(); i++) {

            String id = receiptsList.get(i).getId();
            String propertyId = receiptsList.get(i).getPropertyId();
            Properties properties = propertiesService.getPropertyById(propertyId);
            String propertyName = properties.getPropertyName();

            String amountPaid = receiptsList.get(i).getAmountPaid() + " Kshs";
            String referenceNo = receiptsList.get(i).getReceiptReference();
            String datePaid = sdf.format(receiptsList.get(i).getDatePaid());

            DbReceiptsData dbReceiptsData = new DbReceiptsData(id, propertyName, referenceNo, amountPaid, datePaid);
            dbReceiptsDataList.add(dbReceiptsData);

        }


        DbInformation getNoticies = notificationServiceImpl.getNotices();

        List<NotificationDetails> noticeList = getNoticies.getNoticeBoard().getNotices();
        List<NotificationDetails> rentPaidList = getNoticies.getNoticeBoard().getRentPaid();
        List<NotificationDetails> overDueList = getNoticies.getNoticeBoard().getOverdueRent();


        for (DbPropetiesData properties : propertyList) {

            String propertyName = properties.getPropertyName();
            propertyNameList.add(propertyName);
        }

        List<DBReceiptProperty> dbReceiptPropertyList = receiptsServiceImpl.getPropertyReceipts();
        List<DbExpenseProperty> dbExpensePropertyList = expensesServiceImpl.getExpenseProperty();

        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("propertyList", propertyList);
        modelAndView.addObject("propertyNameList", propertyNameList);
        modelAndView.addObject("receiptsList", dbReceiptsDataList);
        modelAndView.addObject("noticeList", noticeList);
        modelAndView.addObject("rentPaidList", rentPaidList);
        modelAndView.addObject("dbReceiptPropertyList", dbReceiptPropertyList);
        modelAndView.addObject("dbExpensePropertyList", dbExpensePropertyList);
        modelAndView.addObject("overDueList", overDueList);
        modelAndView.addObject("expensesList", dbExpensesDataList);

        return modelAndView;
    }

    @RequestMapping(value = "/property_details/{propertyId}", method = RequestMethod.GET)
    public ModelAndView getCart(@PathVariable("propertyId") String propertyId) {

        DbPropetiesData propertyDetails = propertiesService.getAllPropertyDetails(propertyId);

        List<Receipts> receiptsList = receiptsService.getReceiptDetailsByPropertyId(propertyId);
        List<Expenses> expenseList = expensesServiceImpl.getMyPropertyExpenses(propertyId).getResults();

        double totalExpenseAmount = 0.0;
        for (Expenses receipts : expenseList) {

            Double amountPaid = receipts.getExpenseAmount();
            totalExpenseAmount = totalExpenseAmount + amountPaid;
        }
        String totalReceipts = "Kshs " + totalExpenseAmount ;

        double totalReceiptAmount = 0.0;
        for (Receipts receipts : receiptsList) {

            Double amountPaid = receipts.getAmountPaid();
            totalReceiptAmount = totalReceiptAmount + amountPaid;
        }
        String totalExpenses = "Kshs " + totalReceiptAmount ;

        PropertyInfo propertyInfo = new PropertyInfo(propertyDetails,receiptsList,expenseList,totalReceipts,totalExpenses);

        ModelAndView modelAndView = new ModelAndView("property_details");
        modelAndView.addObject("title", "Property Management.");
        modelAndView.addObject("content", "Property Details.");
        modelAndView.addObject("propertyInfo", propertyInfo);

        return modelAndView;

    }

}
