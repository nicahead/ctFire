package com.nic.ctFire.web.device.service.fallback;

import com.nic.ctFire.common.hystrix.Fallback;
import com.nic.ctFire.web.device.service.DeviceService;
import org.springframework.stereotype.Component;

@Component
public class DeviceServiceFallback implements DeviceService {

    @Override
    public String save(String deviceJson) {
        return Fallback.badGateway();
    }

    @Override
    public String delete(String id) {
        return Fallback.badGateway();
    }

    @Override
    public String get(String id) {
        return null;
    }

    @Override
    public String close(String id) {
        return null;
    }

    @Override
    public String page(int start, int length, String deviceJson) {
        return Fallback.badGateway();
    }

}
