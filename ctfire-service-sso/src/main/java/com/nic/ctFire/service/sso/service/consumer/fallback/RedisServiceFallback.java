package com.nic.ctFire.service.sso.service.consumer.fallback;

import com.nic.ctFire.service.sso.service.consumer.RedisService;
import org.springframework.stereotype.Component;

/**
 * 熔断器回调函数
 * 为了保证其高可用，单个服务通常会集群部署。由于网络原因或者自身的原因，服务并不能保证 100% 可用
 * 如果单个服务出现问题，调用这个服务就会出现线程阻塞，此时若有大量的请求涌入，Servlet 容器的线程资源会被消耗完毕
 * 导致服务瘫痪。服务与服务之间的依赖性，故障会传播，会对整个微服务系统造成灾难性的严重后果
 * 这就是服务故障的 “雪崩” 效应
 * 为了解决这个问题，业界提出了熔断器模型
 */

@Component
public class RedisServiceFallback implements RedisService {
    @Override
    public String put(String key, String value, long seconds) {
        return null;
    }

    @Override
    public String get(String key) {
        return null;
    }
}
