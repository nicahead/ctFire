package com.nic.ctFire.web.device.interceptor;

import com.nic.ctFire.common.utils.CookieUtils;
import com.nic.ctFire.common.web.interceptor.BaseInterceptorMethods;
import com.nic.ctFire.web.device.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    private static String HOSTS_DEVICE;

    @Value("${hosts.device}")
    public void setHostsDevice(String hostsDevice) {
        HOSTS_DEVICE = hostsDevice;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return BaseInterceptorMethods.preHandleForLogin(request, response, handler, HOSTS_DEVICE + request.getServletPath());
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        String token = CookieUtils.getCookieValue(request, "token");
        if (StringUtils.isNotBlank(token)) {
            String username = redisService.get(token);
            if (StringUtils.isNotBlank(username)) {
                BaseInterceptorMethods.postHandleForLogin(request, response, handler, modelAndView, redisService.get(username), HOSTS_DEVICE + request.getServletPath());
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
