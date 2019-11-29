package com.nic.ctFire.web.user.service;

import com.nic.ctFire.web.user.service.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ctFire-service-user",fallback = UserServiceFallback.class)
public interface UserService {

    @PostMapping(value = "save")
    public String save(@RequestParam(required = true) String userJson);

    @PostMapping(value = "delete")
    public String delete(@RequestParam(required = true) String ids);

    @GetMapping(value = "get")
    public String get(@RequestParam(required = true) String id);

    @GetMapping(value = "page")
    public String page(@RequestParam(required = true) int start,
                       @RequestParam(required = true) int length,
                       @RequestParam(required = true) String userJson);
}
