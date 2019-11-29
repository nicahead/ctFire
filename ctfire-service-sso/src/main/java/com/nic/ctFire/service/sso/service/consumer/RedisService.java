package com.nic.ctFire.service.sso.service.consumer;

import com.nic.ctFire.service.sso.service.consumer.fallback.RedisServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用feign消费redis服务提供者，需要写一个接口
 * 方法和redis服务提供者的controller中的方法一致
 * 这样就可以在本服务中调用接口的方法来进行消费
 */
@FeignClient(value = "ctFire-service-redis",fallback = RedisServiceFallback.class)
public interface RedisService {

    @PostMapping(value = "put")
    public String put(@RequestParam(value = "key") String key,
                      @RequestParam(value = "value") String value,
                      @RequestParam(value = "seconds") long seconds);

    @GetMapping(value = "get")
    public String get(@RequestParam(value = "key") String key);
}
