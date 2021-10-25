package com.properties.propertiesapp.authentication.helperclass

import com.properties.propertiesapp.authentication.entity.StaffDetails


data class Results(
    val statusCode: Int,
    val details: Any
)
data class ErrorMessage(
    val error: String
)
data class SuccessMessage(
    val details: String

)
data class SuccessfulLogin(
    val userDetails: StaffDetails,
    val accessToken : String
)
data class LoginResponse(

    val accessToken:String,
    val userId:String,
    val firstName:String,
    val emailAddress:String,
    val lastName:String,
    val phoneNumber:String,
    val roles:List<String>,
)


