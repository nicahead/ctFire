package com.nic.ctFire.service.redis.controller;

import com.nic.ctFire.service.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {
    @Autowired
    private RedisService redisService;

    @PostMapping(value = "put")
    public String put(String key, String value, long seconds) {
        redisService.put(key, value, seconds);
        return "ok";
    }

    @GetMapping(value = "get")
    public String get(String key) {
        Object res = redisService.get(key);
        if (res != null) {
            String resJson = String.valueOf(res);
            return resJson;
        }
        return null;
    }
}
