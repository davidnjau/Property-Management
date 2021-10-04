package com.properties.propertiesapp.service_class.Impl;

import com.properties.propertiesapp.entity.Expenses;
import com.properties.propertiesapp.entity.Properties;
import com.properties.propertiesapp.helper_class.*;
import com.properties.propertiesapp.repository.ExpensesRepository;
import com.properties.propertiesapp.service_class.service.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class ExpensesServiceImpl implements ExpensesService {

    @Autowired
    private PropertiesServiceImpl propertiesServiceImpl;

    @Autowired
    private ExpensesRepository expensesRepository;

    @Autowired
    private PropertiesServiceImpl propertiesService;


    @Override
    public Expenses addExpense(Expenses expenses) {
        return expensesRepository.save(expenses);
    }

    @Override
    public List<Expenses> getAllExpenses() {
        return findPaginatedExpenses();
    }

    @Override
    public List<Expenses> getPropertyExpenses(String propertyId) {
        return expensesRepository.findAllByPropertyId(propertyId);
    }

    @Override
    public Expenses getExpenseById(String expenseId) {

        Optional<Expenses> optionalExpenses = expensesRepository.findById(expenseId);
        return optionalExpenses.orElse(null);

    }

    @Override
    public void deleteExpense(String expenseId) {
        expensesRepository.deleteById(expenseId);
    }

    @Override
    public void deletePropertyExpense(String propertyId) {

        List<Expenses> expenseList = getPropertyExpenses(propertyId);
        if (!expenseList.isEmpty()){
            expensesRepository.deleteByPropertyId(propertyId);
        }

    }

    /**
     * Model
     */
    public Expenses saveExpense(DbExpenses dbExpenses, String expenseType){

        String propertyName = dbExpenses.getPropertyName();
        Double expenseAmount = dbExpenses.getExpenseAmount();
        String expenseName = dbExpenses.getExpenseName();
        Date expenseDate = dbExpenses.getExpenseDate();

        Properties properties = propertiesServiceImpl.findPropertyByName(propertyName);
        String propertyId = properties.getId();

        Expenses expenses = new Expenses(propertyId, expenseName, expenseAmount, expenseDate, expenseType);

        return addExpense(expenses);

    }

    public DbExpensesResults getAllAvailableExpenses(){

        try{

            List<Expenses> expensesList = getAllExpenses();

            return new DbExpensesResults(
                    expensesList.size(),
                    null,
                    null,
                    expensesList
            );

        }catch (Exception e){
            return null;
        }


    }

    public DbExpensesData getExpenseDetails(String expenseId){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");

        Expenses expenses = getExpenseById(expenseId);
        if (expenses != null){

            String id = expenses.getId();
            String expenseName = expenses.getExpenseName();
            Double expenseAmount = expenses.getExpenseAmount();
            Date expenseDate = expenses.getExpenseDate();
            String expenseType = expenses.getExpenseType();
            String expenseDateFormat = sdf.format(expenseDate);
            String formAmount = expenseAmount + " Kshs";

            String propertyId = expenses.getPropertyId();

            String propertyName = propertiesService.getPropertyById(propertyId).getPropertyName();


            return new DbExpensesData(
                    id, propertyName, expenseName, formAmount, expenseDateFormat, expenseType
            );
        }else {
            return null;
        }
    }

    public DbExpensesResults getMyPropertyExpenses(String propertyId){
        try{

            List<Expenses> expensesList = getPropertyExpenses(propertyId);

            return new DbExpensesResults(
                    expensesList.size(),
                    null,
                    null,
                    expensesList
            );

        }catch (Exception e){
            return null;
        }
    }

    public List<DbExpenseProperty> getExpenseProperty(){

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");

        List<DbExpenseProperty> dbExpensePropertyList = new ArrayList<>();
        List<Properties> propertyList = propertiesServiceImpl.getAllProperty();
        for (int i = 0; i < propertyList.size(); i++){

            String propertyOccupancyDate = sdf.format(propertyList.get(i).getPropertyOccupancyDate());

            boolean isVat = propertyList.get(i).isVat();
            String propertyVatStatus = "";
            if (isVat){
                propertyVatStatus = "The property has a 16 % VAT.";
            }else {
                propertyVatStatus = "The property does not have any VAT.";
            }
            String propertyId = propertyList.get(i).getId();
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

            List<Expenses> propertyExpenseList = getPropertyExpenses(propertyId);
            if (!propertyExpenseList.isEmpty()){

                DbPropetiesData dbPropetiesData = new DbPropetiesData(
                        propertyId, propertyOccupancyDate, propertyVatStatus, propertyName, propertyLocation, propertyDetails,
                        propertyLandlordDetails, paymentSchedule, propertyTenancyPeriod, rentAmount,propertyRentAmount,
                        incrementalData, propertyDepositAmount);

                List<DbExpensesData> dbExpensesDataList = new ArrayList<>();
                for (int j = 0; j < propertyExpenseList.size(); j++){

                    String id = propertyExpenseList.get(j).getId();
                    String expenseName = propertyExpenseList.get(j).getExpenseName();
                    Double expenseAmount = propertyExpenseList.get(j).getExpenseAmount();
                    Date expenseDate = propertyExpenseList.get(j).getExpenseDate();
                    String expenseType = propertyExpenseList.get(j).getExpenseType();

                    String strDate = sdf.format(expenseDate);
                    String newAmount = expenseAmount + " Kshs";

                    DbExpensesData dbExpensesData = new DbExpensesData(id, propertyName, expenseName, newAmount, strDate, expenseType);
                    dbExpensesDataList.add(dbExpensesData);

                }

                DbExpenseProperty dbExpenseProperty = new DbExpenseProperty(dbPropetiesData, dbExpensesDataList);
                dbExpensePropertyList.add(dbExpenseProperty);

            }

        }

        return dbExpensePropertyList;

    }

    public List<Expenses> findPaginatedExpenses(){

        int pageSize = 10;
        String sortPageField = "";
        sortPageField = "createdAt";
        Sort sort = Sort.by(sortPageField).descending();
        Pageable pageable = PageRequest.of(0, pageSize, sort);
        Page<Expenses> expensesPage = expensesRepository.findAll(pageable);
        return expensesPage.getContent();

    }

}
