package com.properties.propertiesapp.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Properties {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uui_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private Date propertyOccupancyDate;

    private boolean isVat;

    @Column(unique = true)
    private String propertyName;
    private String propertyLocation;
    private String propertyDetails;
    private String propertyLandlordDetails;
    private String paymentSchedule;

    private Double propertyTenancyPeriod;
    private Double propertyRentAmount;
    private Double incrementalPerc;
    private Double propertyDepositAmount;

    public Properties() {
    }

    public Properties(String propertyName, String propertyLocation, String propertyDetails,
                      String propertyLandlordDetails, boolean isVat, Double incrementalPerc,
                      Double propertyDepositAmount, Date propertyOccupancyDate,
                      Double propertyTenancyPeriod, String paymentSchedule, Double propertyRentAmount) {
        this.propertyName = propertyName;
        this.propertyLocation = propertyLocation;
        this.propertyDetails = propertyDetails;
        this.propertyLandlordDetails = propertyLandlordDetails;
        this.isVat = isVat;
        this.incrementalPerc = incrementalPerc;
        this.propertyDepositAmount = propertyDepositAmount;
        this.propertyOccupancyDate = propertyOccupancyDate;
        this.propertyTenancyPeriod = propertyTenancyPeriod;
        this.paymentSchedule = paymentSchedule;
        this.propertyRentAmount = propertyRentAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updatedAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updatedAt = updateAt;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyLocation() {
        return propertyLocation;
    }

    public void setPropertyLocation(String propertyLocation) {
        this.propertyLocation = propertyLocation;
    }

    public String getPropertyDetails() {
        return propertyDetails;
    }

    public void setPropertyDetails(String propertyDetails) {
        this.propertyDetails = propertyDetails;
    }

    public String getPropertyLandlordDetails() {
        return propertyLandlordDetails;
    }

    public void setPropertyLandlordDetails(String propertyLandlordDetails) {
        this.propertyLandlordDetails = propertyLandlordDetails;
    }

    public boolean isVat() {
        return isVat;
    }

    public void setVat(boolean vat) {
        isVat = vat;
    }

    public Double getIncrementalPerc() {
        return incrementalPerc;
    }

    public void setIncrementalPerc(Double incrementalPerc) {
        this.incrementalPerc = incrementalPerc;
    }

    public Double getPropertyDepositAmount() {
        return propertyDepositAmount;
    }

    public void setPropertyDepositAmount(Double propertyDepositAmount) {
        this.propertyDepositAmount = propertyDepositAmount;
    }

    public Date getPropertyOccupancyDate() {
        return propertyOccupancyDate;
    }

    public void setPropertyOccupancyDate(Date propertyOccupancyDate) {
        this.propertyOccupancyDate = propertyOccupancyDate;
    }

    public Double getPropertyTenancyPeriod() {
        return propertyTenancyPeriod;
    }

    public void setPropertyTenancyPeriod(Double propertyTenancyPeriod) {
        this.propertyTenancyPeriod = propertyTenancyPeriod;
    }

    public String getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(String paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

    public Double getPropertyRentAmount() {
        return propertyRentAmount;
    }

    public void setPropertyRentAmount(Double propertyRentAmount) {
        this.propertyRentAmount = propertyRentAmount;
    }
}
