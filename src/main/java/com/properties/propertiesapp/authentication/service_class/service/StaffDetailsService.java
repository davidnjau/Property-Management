package com.properties.propertiesapp.authentication.service_class.service;


import com.properties.propertiesapp.authentication.entity.StaffDetails;
import java.util.List;

public interface StaffDetailsService {

    StaffDetails addStaffDetails(StaffDetails userDetails);
    List<StaffDetails> getAllUsers();
//    List<StaffDetails> getRegNonUsers(Boolean verified);
    StaffDetails getStaffDetailsByEmailAddress(String emailAddress);
//    boolean isVerified(String userId);
    boolean isEmailAddress(String emailAddress);
    boolean isUserName(String userName);

}
