package com.onetrust.LocationInfo.controller;

import com.onetrust.LocationInfo.Model.IpDto;
import com.onetrust.LocationInfo.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ip")
public class GeoLocationController {
    @Autowired
    public IpService ipservice;

    @GetMapping
    @ResponseBody
    public IpDto getIP() throws Exception {
        return ipservice.getClient();
    }
}