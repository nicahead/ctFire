package com.nic.ctFire.web.device.service.fallback;

import com.nic.ctFire.web.device.service.DataService;
import org.springframework.stereotype.Component;

@Component
public class DataServiceFallback implements DataService {

    @Override
    public String save(String dataJson) {
        return null;
    }

    @Override
    public String page(int start, int length, String dataJson) {
        return null;
    }
}
