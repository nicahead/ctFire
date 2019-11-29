package com.nic.ctFire.web.user.service.fallback;

import com.nic.ctFire.common.hystrix.Fallback;
import com.nic.ctFire.web.user.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallback implements UserService {

    @Override
    public String save(String userJson) {
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
    public String page(int start, int length, String userJson) {
        return Fallback.badGateway();
    }

}
