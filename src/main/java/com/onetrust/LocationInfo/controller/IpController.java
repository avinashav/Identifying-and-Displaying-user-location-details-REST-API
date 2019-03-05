package com.onetrust.LocationInfo.controller;

import com.onetrust.LocationInfo.Model.IpDto;
import com.onetrust.LocationInfo.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ip")
public class IpController{
    @Autowired
    public IpService ipservices;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public IpDto getIP() throws Exception {
        return ipservices.getClient();
    }
}