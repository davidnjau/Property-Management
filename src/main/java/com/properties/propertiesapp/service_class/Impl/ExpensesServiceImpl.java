package com.properties.propertiesapp.service_class.Impl;

import com.properties.propertiesapp.entity.Expenses;
import com.properties.propertiesapp.entity.Properties;
import com.properties.propertiesapp.helper_class.DbExpenses;
import com.properties.propertiesapp.helper_class.DbExpensesData;
import com.properties.propertiesapp.helper_class.DbExpensesResults;
import com.properties.propertiesapp.helper_class.DbResults;
import com.properties.propertiesapp.repository.ExpensesRepository;
import com.properties.propertiesapp.service_class.service.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
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
        return expensesRepository.findAll();
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

}
