package com.onetrust.LocationInfo.service;
import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;
import com.onetrust.LocationInfo.model.GeoLocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import org.apache.log4j.Logger;

@Service
public class GeoLocationServiceImpl implements GeoLocationService{

    private static final Logger LOGGER = Logger.getLogger(GeoLocationServiceImpl.class);
    @Autowired
    private HttpServletRequest request;

    /*Serving the Current HTTP Request*/
    public GeoLocationServiceImpl(HttpServletRequest request) {
        this.request = request;
    }

    public GeoLocationDto getClient() throws AddressNotFoundException {
        String remoteAddr = "";
            if (request != null) {
                remoteAddr = request.getHeader("X-FORWARDED-FOR");
                if (remoteAddr == null || "".equals(remoteAddr)) {
                    remoteAddr = request.getRemoteAddr();
                }
            }

        /*calls max-mind geoIP package to get client-IP details*/
        return locationDetails(remoteAddr);
    }

    public GeoLocationDto locationDetails(String searchIpAddress){

        final GeoLocationDto GeoLocationDto = new GeoLocationDto();
        String countryName = "";
        String countryCode = "";

       /*Country Service - GeoIP2 Java API
        This creates a WebServiceClient object that is thread-safe and can be
        reused across requests. Reusing the object will allow it to keep
        connections alive for future requests. The object is closeable, but
        it should not be closed until you are finished making requests with it.
         */
        try (WebServiceClient client = new WebServiceClient.Builder(139613, "juLizFqIbkkQ")
                .build()) {

            InetAddress ipAddress = InetAddress.getByName(searchIpAddress);
            CountryResponse response = client.country(ipAddress);

            Country country = response.getCountry();
            countryCode = country.getIsoCode();
            countryName = country.getName();

            GeoLocationDto.setIpAddress(searchIpAddress);
            GeoLocationDto.setCountryCode(countryCode);
            GeoLocationDto.setCountryName(countryName);

        } catch (IOException e) {
            LOGGER.error(e);
        }
        catch (GeoIp2Exception e) {
            LOGGER.error(e);
            GeoLocationDto.setIpAddress(searchIpAddress);
            GeoLocationDto.setCountryCode(e.getMessage());
            GeoLocationDto.setCountryName(e.getMessage());
        }

        return GeoLocationDto;
    }

}