package com.onetrust.LocationInfo.model;

public class GeoLocationDto {
    private String ipAddress;
    private String countryCode;
    private String countryName;

    public GeoLocationDto() {
        this.ipAddress = "";
        this.countryCode = "";
        this.countryName = "";
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return "IpEntity{" +
                "IpAddress='" + ipAddress + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}
