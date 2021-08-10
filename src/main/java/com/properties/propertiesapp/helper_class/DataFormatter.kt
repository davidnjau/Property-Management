package com.properties.propertiesapp.helper_class

import com.properties.propertiesapp.entity.Properties
import com.properties.propertiesapp.service_class.Impl.EmailServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DataFormatter {


    fun sendPropertyMail(
        emailService: EmailServiceImpl,
        addedProperty: Properties
    ) {
        CoroutineScope(Dispatchers.IO).launch { emailService.sendSavedProperty(addedProperty) }
    }
    fun sendReceiptMail(
        emailService: EmailServiceImpl,
        addedProperty: DbReceiptsData
    ) {
        CoroutineScope(Dispatchers.IO).launch { emailService.sendSavedReceipt(addedProperty) }
    }



}