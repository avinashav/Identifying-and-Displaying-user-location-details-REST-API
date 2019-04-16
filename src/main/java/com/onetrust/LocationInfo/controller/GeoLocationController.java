package com.onetrust.LocationInfo.controller;

import com.maxmind.geoip2.exception.AddressNotFoundException;
import org.apache.log4j.Logger;
import com.onetrust.LocationInfo.model.GeoLocationDto;
import com.onetrust.LocationInfo.service.GeoLocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/ip")
public class GeoLocationController {
    private static final Logger logger = Logger.getLogger(GeoLocationController.class);
    @Autowired
    public GeoLocationServiceImpl ipservice;

    @GetMapping
    public GeoLocationDto getIP(){
        GeoLocationDto obj = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("Our application is executed!");
            }
            obj = ipservice.getClient();
        }
        catch (AddressNotFoundException e) {
            logger.error(e);
        }
        return obj;
    }
}