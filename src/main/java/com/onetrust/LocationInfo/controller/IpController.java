package com.onetrust.LocationInfo.controller;

import com.onetrust.LocationInfo.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IpController{
    @Autowired
    public IpService services;

    public String getIP(){
        return services.getClient();
    }

}