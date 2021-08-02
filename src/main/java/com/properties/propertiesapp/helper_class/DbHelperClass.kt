package com.properties.propertiesapp.helper_class

import com.properties.propertiesapp.entity.Expenses
import com.properties.propertiesapp.entity.Properties
import com.properties.propertiesapp.entity.Receipts
import java.util.*

data class DbResults(
    val count: Int,
    val next : String?,
    val previous: String?,
    val results: List<Properties>
)
data class DbResults1(
    val count: Int,
    val next : String?,
    val previous: String?,
    val results: List<DbPropetiesData>
)
data class DbExpensesResults(
    val count: Int,
    val next : String?,
    val previous: String?,
    val results: List<Expenses>
)
data class DbPaymentReceipt(
    val count: Int,
    val next : String?,
    val previous: String?,
    val results: List<Receipts>
)
data class DbPropetiesData(
    val id : String,
    val propertyOccupancyDate: String,
    val propertyVatStatus : String,
    val propertyName: String,
    val propertyLocation: String,
    val propertyDetails: String,
    val propertyLandlordDetails: String,
    val paymentSchedule: String,
    val propertyTenancyPeriod: String,
    val propertyRent: String,
    val propertyRentAmount: Double,
    val incrementalPerc: String,
    val propertyDepositAmount: String,
)
data class ErrorMessage(
    val error : String
)
data class Results(
    val statusCode: Int,
    val details : Any
)
data class DbRentPaid(
    val rentPaid : List<Double>,
    val rentArrear : List<Double>,
)

data class DbInformation(
    val noticesCount: Int,
    val rentPaidCount: Int,
    val overdueRentCount: Int,
    val noticeBoard: DbNotifications
)

data class DbNotifications(
    val notices: List<NotificationDetails>,
    val rentPaid: List<NotificationDetails>,
    val overdueRent: List<NotificationDetails>
)
data class NotificationDetails(
    val notification_type: String,
    val message : String,
    val time: String

)
data class DbExpensesData(
    val id: String,
    val propertyName: String,
    val expenseName: String,
    val expenseAmount: String,
    val expenseDate: String,
    val expenseType: String
)
data class DbReceiptsData(
    val id: String,
    val propertyName: String,
    val receiptReference: String,
    val rentAmount: String,
    val datePaid: String
)