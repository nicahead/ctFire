package com.nic.ctFire.service.device.service.consumer.fallback;

import com.nic.ctFire.service.device.service.consumer.DataService;
import org.springframework.stereotype.Component;

@Component
public class DataServiceFallback implements DataService {

    @Override
    public String save(String dataJson) {
        return null;
    }

}
