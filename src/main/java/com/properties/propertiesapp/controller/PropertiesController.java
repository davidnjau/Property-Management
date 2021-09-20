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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
public class PropertiesController {

    @Autowired
    private PropertiesServiceImpl propertiesServiceImpl;

    @Autowired
    private ReceiptsServiceImpl receiptsServiceImpl;

    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    @Autowired
    private ExpensesServiceImpl expensesServiceIml;

    @RequestMapping(value = "/api/v1/properties/add_property", method = RequestMethod.POST)
    public ResponseEntity addProperty(@RequestBody Properties properties){

        Results addedProperties = propertiesServiceImpl.addProperty(properties);
        if (addedProperties != null){

            var statusCode = addedProperties.getStatusCode();
            var results = addedProperties.getDetails();
            if (statusCode == 200){
                return new ResponseEntity(results, HttpStatus.OK);
            }else {
                var results1 = results.toString();

                return ResponseEntity.badRequest().body(new ErrorMessage(results1));

            }



        }else {
            return ResponseEntity.internalServerError().body(new ErrorMessage("Please try again."));
        }
    }

    @RequestMapping(value = "/api/v1/properties/get_property/", method = RequestMethod.GET)
    public ResponseEntity getProperty(){

        DbResults1 getProperties = propertiesServiceImpl.getAllPropertyData();
        if (getProperties != null){

            return new ResponseEntity(getProperties, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("Please try again."));
        }
    }

    @RequestMapping(value = "/api/v1/properties/get_property/{property_id}", method = RequestMethod.GET)
    public ResponseEntity getPropertyDetails(@PathVariable("property_id") String property_id){

        DbPropetiesData getProperties = propertiesServiceImpl.getAllPropertyDetails(property_id);
        if (getProperties != null){

            return new ResponseEntity(getProperties, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("There is no property associated with that id"));
        }
    }

    @RequestMapping(value = "/api/v1/receipts/add_receipts", method = RequestMethod.POST)
    public ResponseEntity addPaymentReceipt(@RequestBody DbReceipts receipts){
        Receipts addedReceipt = receiptsServiceImpl.saveReceipt(receipts);
        if (addedReceipt != null){

            return new ResponseEntity(addedReceipt, HttpStatus.OK);

        }else {
            return ResponseEntity.internalServerError().body(new ErrorMessage("Failed to add property. Please try again."));
        }
    }

    @RequestMapping(value = "/api/v1/receipts/get_receipts/", method = RequestMethod.GET)
    public ResponseEntity getPayementReceipts(){

        DbPaymentReceipt getProperties = receiptsServiceImpl.getAllReceiptData();
        if (getProperties != null){

            return new ResponseEntity(getProperties, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("Please try again."));
        }
    }

    @RequestMapping(value = "/api/v1/receipts/get_receipts/{receipt_id}", method = RequestMethod.GET)
    public ResponseEntity getPayementReceiptsDetails(@PathVariable("receipt_id") String receipt_id){

        DbReceiptsData receiptDetails = receiptsServiceImpl.getReceiptDataDetails(receipt_id);
        if (receiptDetails != null){

            return new ResponseEntity(receiptDetails, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("Please try again."));
        }
    }

    @RequestMapping(value = "/api/v1/receipts/get_monthly_payments/", method = RequestMethod.GET)
    public ResponseEntity getMonthlyPayments(){

        List<Double> getProperties = receiptsServiceImpl.getMonthlyRent();
        if (getProperties != null){

            return new ResponseEntity(getProperties, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("Please try again."));
        }
    }
    @RequestMapping(value = "/api/v1/receipts/get_monthly_deficits/", method = RequestMethod.GET)
    public ResponseEntity getMonthlyDesficits(){

        List<Double> getProperties = receiptsServiceImpl.getRentDeficits();
        if (getProperties != null){

            return new ResponseEntity(getProperties, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("Please try again."));
        }
    }

    @RequestMapping(value = "/api/v1/receipts/get_monthly_comparison/", method = RequestMethod.GET)
    public ResponseEntity getMonthlyComparison(){

        DbRentPaid getProperties = receiptsServiceImpl.getRentComparison();
        if (getProperties != null){

            return new ResponseEntity(getProperties, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("Please try again."));
        }
    }

    @RequestMapping(value = "/api/v1/notifications/get_notifications/", method = RequestMethod.GET)
    public ResponseEntity getNotifications(){

        DbInformation getNoticies = notificationServiceImpl.getNotices();
        if (getNoticies != null){

            return new ResponseEntity(getNoticies, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("Please try again."));
        }
    }

    @RequestMapping(value = "/api/v1/expenses/add-utility-expenses", method = RequestMethod.POST)
    public ResponseEntity addUtilityExpenses(@RequestBody DbExpenses dbExpenses){

        String utilities = Expense.UTILITIES.name();

        Expenses expenses = expensesServiceIml.saveExpense(dbExpenses, utilities);
        if (expenses != null){
            return new ResponseEntity(expenses, HttpStatus.OK);
        }else {
            return ResponseEntity.internalServerError().body(new ErrorMessage("Failed to add the expense. Please try again."));
        }
    }
    @RequestMapping(value = "/api/v1/expenses/add-extra-expenses", method = RequestMethod.POST)
    public ResponseEntity addExtraExpenses(@RequestBody DbExpenses dbExpenses){

        String extra = Expense.EXTRA.name();

        Expenses expenses = expensesServiceIml.saveExpense(dbExpenses, extra);
        if (expenses != null){
            return new ResponseEntity(expenses, HttpStatus.OK);
        }else {
            return ResponseEntity.internalServerError().body(new ErrorMessage("Failed to add the expense. Please try again."));
        }
    }

    @RequestMapping(value = "/api/v1/expenses/get-all-expenses/", method = RequestMethod.GET)
    public ResponseEntity getAllExpenses(){

        DbExpensesResults dbExpensesResults = expensesServiceIml.getAllAvailableExpenses();
        if (dbExpensesResults != null){

            return new ResponseEntity(dbExpensesResults, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("Please try again."));
        }
    }
    @RequestMapping(value = "/api/v1/expenses/get-expense/{expense_id}", method = RequestMethod.GET)
    public ResponseEntity getExpensesDetails(@PathVariable("expense_id") String expense_id){

        DbExpensesData expenseDetails = expensesServiceIml.getExpenseDetails(expense_id);
        if (expenseDetails != null){

            return new ResponseEntity(expenseDetails, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("There is no property associated with that id"));
        }
    }
    @RequestMapping(value = "/api/v1/expenses/get-property-expense/{property_id}", method = RequestMethod.GET)
    public ResponseEntity getPropertyExpensesDetails(@PathVariable("property_id") String property_id){

        DbExpensesResults dbExpensesResults = expensesServiceIml.getMyPropertyExpenses(property_id);
        if (dbExpensesResults != null){

            return new ResponseEntity(dbExpensesResults, HttpStatus.OK);

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("There is no property associated with that id"));
        }
    }

    @RequestMapping(value = "/api/v1/properties/update_property", method = RequestMethod.PUT)
    public ResponseEntity updateUserDetails(@RequestBody Properties properties) throws UnsupportedEncodingException {

        Properties cafeteriaMeals = propertiesServiceImpl.updateProperty(properties);
        if (cafeteriaMeals != null){
            return new ResponseEntity(cafeteriaMeals, HttpStatus.OK);
        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("There was an issue. Try again"));
        }


    }
    @RequestMapping(value = "/api/v1/receipts/update_receipts", method = RequestMethod.PUT)
    public ResponseEntity updateUserDetails(@RequestBody Receipts receipts) throws UnsupportedEncodingException {

        Receipts receipts1 = receiptsServiceImpl.updateReceipts(receipts);
        if (receipts1 != null){
            return new ResponseEntity(receipts1, HttpStatus.OK);
        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("There was an issue. Try again"));
        }


    }




}
