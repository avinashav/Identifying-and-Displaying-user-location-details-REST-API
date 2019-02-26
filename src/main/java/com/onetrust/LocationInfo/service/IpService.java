package com.onetrust.LocationInfo.service;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Service
@RequestMapping(value = "/ip")
public class IpService {
    @Autowired
    private HttpServletRequest request;

    public IpService(HttpServletRequest request) {
        this.request = request;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getClient() throws Exception {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");

            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        System.out.println(map);

        return remoteAddr;
        // return tellCountry(remoteAddr);
    }

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

}