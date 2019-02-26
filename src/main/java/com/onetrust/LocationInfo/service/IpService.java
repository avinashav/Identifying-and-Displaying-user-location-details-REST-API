package com.onetrust.LocationInfo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

@Service
@RequestMapping(value = "ip")
public class IpService {
    @Autowired
    private HttpServletRequest request;

    public IpService(HttpServletRequest request) {
        this.request = request;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getClient() {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
}
