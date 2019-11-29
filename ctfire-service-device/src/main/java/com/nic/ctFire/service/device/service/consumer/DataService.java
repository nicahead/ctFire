package com.nic.ctFire.service.device.service.consumer;

import com.nic.ctFire.service.device.service.consumer.fallback.DataServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ctFire-service-data",fallback = DataServiceFallback.class)
public interface DataService {

    @PostMapping(value = "save")
    public String save(@RequestParam(required = true) String dataJson);

}
