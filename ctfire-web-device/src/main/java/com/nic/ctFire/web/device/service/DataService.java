package com.nic.ctFire.web.device.service;

import com.nic.ctFire.web.device.service.fallback.DataServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ctFire-service-data",fallback = DataServiceFallback.class)
public interface DataService {

    @PostMapping(value = "save")
    public String save(@RequestParam(required = true) String dataJson);

    @GetMapping(value = "page")
    public String page(@RequestParam(required = true) int start,
                       @RequestParam(required = true) int length,
                       @RequestParam(required = true) String dataJson);
}