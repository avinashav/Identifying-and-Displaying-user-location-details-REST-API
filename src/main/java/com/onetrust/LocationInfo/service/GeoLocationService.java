package com.onetrust.LocationInfo.service;

import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.onetrust.LocationInfo.model.GeoLocationDto;

public interface GeoLocationService {
    public GeoLocationDto getClient() throws AddressNotFoundException;
    public GeoLocationDto locationDetails(String searchIpAddress);
}
