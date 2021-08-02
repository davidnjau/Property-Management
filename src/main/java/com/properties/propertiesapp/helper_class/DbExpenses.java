package com.properties.propertiesapp.helper_class;

import java.util.Date;

public class DbExpenses {

    private String propertyName;
    private String expenseAmount;
    private String expenseName;
    private Date expenseDate;

    public DbExpenses() {
    }

    public DbExpenses(String propertyName, String expenseAmount, String expenseName, Date expenseDate) {
        this.propertyName = propertyName;
        this.expenseAmount = expenseAmount;
        this.expenseName = expenseName;
        this.expenseDate = expenseDate;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }
}
