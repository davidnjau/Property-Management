package com.properties.propertiesapp.controller;

import com.properties.propertiesapp.entity.Properties;
import com.properties.propertiesapp.entity.Receipts;
import com.properties.propertiesapp.helper_class.DbResults;
import com.properties.propertiesapp.service_class.Impl.PropertiesServiceImpl;
import com.properties.propertiesapp.service_class.Impl.ReceiptsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private PropertiesServiceImpl propertiesService;

    @Autowired
    private ReceiptsServiceImpl receiptsServiceImpl;

    @RequestMapping(value ="/")
    public ModelAndView getDashboard(){

        DbResults dbResults = propertiesService.getAllPropertyData();
        List<String> propertyNameList = new ArrayList<>();
        List<Properties> propertyList = dbResults.getResults();
        List<Receipts> receiptsList = receiptsServiceImpl.getAllReceipts();

        for (int i = 0; i < propertyList.size(); i++){

            String propertyName = propertyList.get(i).getPropertyName();
            propertyNameList.add(propertyName);

        }

        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("propertyList", propertyList);
        modelAndView.addObject("propertyNameList", propertyNameList);
        modelAndView.addObject("receiptsList", receiptsList);

        return modelAndView;
    }
}
