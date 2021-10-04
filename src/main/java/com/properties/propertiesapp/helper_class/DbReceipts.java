package com.properties.propertiesapp.helper_class;

import java.util.Date;

public class DbReceipts {

    private String propertyName;
    private double amountPaid;
    private String receiptReference;
    private String receiptDetails;
    private Date datePaid;

    public DbReceipts() {
    }

    public DbReceipts(String propertyName, double amountPaid, String receiptReference, String receiptDetails, Date datePaid) {
        this.propertyName = propertyName;
        this.amountPaid = amountPaid;
        this.receiptReference = receiptReference;
        this.receiptDetails = receiptDetails;
        this.datePaid = datePaid;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getReceiptReference() {
        return receiptReference;
    }

    public void setReceiptReference(String receiptReference) {
        this.receiptReference = receiptReference;
    }

    public String getReceiptDetails() {
        return receiptDetails;
    }

    public void setReceiptDetails(String receiptDetails) {
        this.receiptDetails = receiptDetails;
    }

    public Date getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }
}
