package com.nic.ctFire.web.device.service;

import com.nic.ctFire.web.device.service.fallback.DeviceServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ctFire-service-device",fallback = DeviceServiceFallback.class)
public interface DeviceService {
    @PostMapping(value = "save")
    public String save(@RequestParam(required = true) String deviceJson);

    @PostMapping(value = "delete")
    public String delete(@RequestParam(required = true) String ids);

    @GetMapping(value = "get")
    public String get(@RequestParam(required = true) String id);

    @GetMapping(value = "close")
    public String close(@RequestParam(required = true)String id);

    @GetMapping(value = "page")
    public String page(@RequestParam(required = true) int start,
                       @RequestParam(required = true) int length,
                       @RequestParam(required = true) String deviceJson);
}
