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
        InetAddress address = null;
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
        //return remoteAddr;                        //return IP address of Client
        // return tellCountry(remoteAddr);          //Function-call 3rd Party API to get location details
        return LocationDetails(remoteAddr);         //calls max-mind geoIP package to get client-IP details
    }

    public IpDto LocationDetails(String searchIpAddress){

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
            //System.out.println("Country Code: "+ countryCode + "\n Country Name: " + countryName); // '美国'
            IpDto.setIpAddress(searchIpAddress);
            IpDto.setCountryCode(countryCode);
            IpDto.setCountryName(countryName);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }
//        return "Ip-Address: " +searchMe + "\n country-code: " + countryCode + "\n country-name:" + countryName;
        return IpDto;
    }
/*
    public String tellCountry(String systemipaddress) throws IOException, JSONException {
        URL url = new URL("http://api.db-ip.com/v2/free/" + systemipaddress + "/countryCode");
        URLConnection urlcon = url.openConnection();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
        String line;
        while ((line = br.readLine()) != null)
        {
            sb.append(line);
        }
        //JSONObject json = new JSONObject(sb.toString());
        //return (json.getString("countryCode"));
        return sb.toString();
    }
*/
}