package com.nic.ctFire.common.web.interceptor;

import com.nic.ctFire.common.domain.TbUser;
import com.nic.ctFire.common.utils.CookieUtils;
import com.nic.ctFire.common.utils.MapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 拦截器通用函数
 */
@Component
public class BaseInterceptorMethods {

    private static String HOSTS_SSO;

    @Value("${hosts.sso}")
    public void setHostsSso(String hostsSso) {
        HOSTS_SSO = hostsSso;
    }

    /**
     * 登录方法拦截
     *
     * @param request
     * @param response
     * @param handler
     * @param url      跳转地址
     * @return
     */
    public static boolean preHandleForLogin(HttpServletRequest request, HttpServletResponse response, Object handler, String url) {
        String token = CookieUtils.getCookieValue(request, "token");
        // token 为空表示一定没有登录
        if (StringUtils.isBlank(token)) {
            try {
                //跳转到单点登录服务的登录页
                response.sendRedirect(String.format("%s/login?url=%s", HOSTS_SSO, url));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        //token不为空可能已登录，查看局部会话里有没有user信息
        return true;
    }

    /**
     * 登录方法拦截
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @param tbUserJson 登录用户 JSON 对象
     * @param url           跳转地址
     */
    public static void postHandleForLogin(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView, String tbUserJson, String url) {
        HttpSession session = request.getSession();
        TbUser tbUser = (TbUser) session.getAttribute("user");
        // 局部会话有用户，说明当前系统已登录，整个系统肯定也已登录
        if (tbUser != null) {
            if (modelAndView != null) {
                modelAndView.addObject("user", tbUser);
            }
        }
        // 局部会话没有用户，说明当前系统没有登录，需要到SSO验证有没有登录（缓存中有没有用户）
        else {
            //缓存中有用户数据，SSO认证中心验证通过，创建局部会话
            if (StringUtils.isNotBlank(tbUserJson)) {
                try {
                    // 登录成功，创建局部会话
                    tbUser = MapperUtils.json2pojo(tbUserJson, TbUser.class);
                    if (modelAndView != null) {
                        modelAndView.addObject("user", tbUser);
                    }
                    request.getSession().setAttribute("user", tbUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // 二次确认是否有用户信息
        if (tbUser == null) {
            try {
                //确定没登录，跳转到登录页面
                response.sendRedirect(String.format("%s/login?url=%s", HOSTS_SSO, url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
