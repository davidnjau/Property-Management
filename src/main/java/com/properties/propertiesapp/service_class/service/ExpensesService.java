package com.properties.propertiesapp.service_class.service;

import com.properties.propertiesapp.entity.Expenses;

import java.util.List;

public interface ExpensesService {

    Expenses addExpense(Expenses expenses);
    List<Expenses> getAllExpenses();
    List<Expenses> getPropertyExpenses(String propertyId);
    Expenses getExpenseById(String expenseId);


}
