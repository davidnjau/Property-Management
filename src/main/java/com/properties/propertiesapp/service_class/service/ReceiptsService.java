package com.properties.propertiesapp.service_class.service;

import com.properties.propertiesapp.entity.Receipts;

import java.util.List;

public interface ReceiptsService {
    Receipts addReceipt(Receipts receipt);
    List<Receipts> getAllReceipts();
    Receipts getReceiptDetails(String id);
    List<Receipts> getReceiptDetailsByPropertyId(String propertyId);
}
