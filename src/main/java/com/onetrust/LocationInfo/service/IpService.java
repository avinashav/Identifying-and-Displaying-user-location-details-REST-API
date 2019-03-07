package com.onetrust.LocationInfo.service;
import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;
import com.onetrust.LocationInfo.Model.IpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.*;

@Service
public class IpService {
    @Autowired
    private HttpServletRequest request;
    private IpDto IpDto = new IpDto();

    //Serving the Current HTTP Request
    public IpService(HttpServletRequest request) {
        this.request = request;
    }


    public IpDto getClient() throws Exception {
        String remoteAddr = "";
        //InetAddress address = null;               //for checking IPv4 or IPv6

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");

            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
             //   address = InetAddress.getByName(request.getRemoteAddr());       //For checking IPv4 or IPv6
            }
        }

        //Check IPv6 or IPv4
        /*if (address instanceof Inet6Address) {
            System.out.println("Its an IPV6 address");
        } else if (address instanceof Inet4Address) {
            // It's ipv4
            System.out.println("Its an IPV4 address");
        }
        */
        return locationDetails(remoteAddr);         //calls max-mind geoIP package to get client-IP details
    }

    public IpDto locationDetails(String searchIpAddress){

        String countryName = "";
        String countryCode = "";

        try (WebServiceClient client = new WebServiceClient.Builder(139613, "juLizFqIbkkQ")
                .build()) {

            InetAddress ipAddress = InetAddress.getByName(searchIpAddress);

            // Do the lookup
            CountryResponse response = client.country(ipAddress);

            Country country = response.getCountry();
            countryCode = country.getIsoCode();            // example: 'US'
            countryName = country.getName();               // example: 'United States'

            //Initializing the DTO object with returned values
            IpDto.setIpAddress(searchIpAddress);
            IpDto.setCountryCode(countryCode);
            IpDto.setCountryName(countryName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (GeoIp2Exception e) {
            IpDto.setIpAddress(searchIpAddress);
            IpDto.setCountryCode(e.getMessage());
            IpDto.setCountryName(e.getMessage());
        }

        return IpDto;
    }

}