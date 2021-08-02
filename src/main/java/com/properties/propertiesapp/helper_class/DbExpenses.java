package com.properties.propertiesapp.helper_class;

import java.util.Date;

public class DbExpenses {

    private String propertyName;
    private Double expenseAmount;
    private String expenseName;
    private Date expenseDate;

    public DbExpenses() {
    }

    public DbExpenses(String propertyName, Double expenseAmount, String expenseName, Date expenseDate) {
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

    public Double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(Double expenseAmount) {
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
