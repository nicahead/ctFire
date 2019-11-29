package com.nic.ctFire.service.sso.controller;

import com.nic.ctFire.common.domain.TbUser;
import com.nic.ctFire.common.service.mapper.TbUserMapper;
import com.nic.ctFire.common.utils.CookieUtils;
import com.nic.ctFire.common.utils.MapperUtils;
import com.nic.ctFire.service.sso.service.LoginService;
import com.nic.ctFire.service.sso.service.consumer.RedisService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisService redisService;

    /**
     * 跳转登录页(需判断是否登录过)
     *
     * @return
     */
    @GetMapping(value = "login")
    public String login(
            @RequestParam(required = false) String url,
            HttpServletRequest request, Model model) {
        //key:"token" 值:xxxx -> key:xxxx 值:username(用户名)->key:username value:用户信息 判断是否登录
        //整个系统的token的值是相同的，以拿到的token作为key获取缓存中存储的用户信息的key
        String token = CookieUtils.getCookieValue(request, "token");
        // token 不为空可能已登录
        if (StringUtils.isNotBlank(token)) {
            String username = redisService.get(token);
            if (StringUtils.isNotBlank(username)) {
                String json = redisService.get(username);
                if (StringUtils.isNotBlank(json)) {
                    try {
                        TbUser tbUser = MapperUtils.json2pojo(json, TbUser.class);
                        // 已登录
                        if (tbUser != null) {
                            if (StringUtils.isNotBlank(url)) {
                                return "redirect:" + url;
                            }
                        }
                        // 将登录信息传到登录页
                        model.addAttribute("user", tbUser);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(url)) {
            model.addAttribute("url", url);
        }
        return "login";
    }

    /**
     * 登录业务
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(
            @RequestParam(required = true) String username,
            @RequestParam(required = true) String password,
            @RequestParam(required = false) String url,
            HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        TbUser tbUser = loginService.login(username, password);
        // 登录失败
        if (tbUser == null) {
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误，请重新输入");
        }
        // 登录成功
        else {
            String token = UUID.randomUUID().toString();  //生成token
            String result = redisService.put(token, username, 60 * 60 * 24); // 将 用户名 放入缓存
            if (StringUtils.isNotBlank(result) && "ok".equals(result)) {
                //将token放到cookie，其他服务只要符合同源策略，就能拿到这个token
                CookieUtils.setCookie(request, response, "token", token, 60 * 60 * 24);
                if (StringUtils.isNotBlank(url)) {
                    return "redirect:" + url;
                }
            }
            // 熔断处理
            else {
                redirectAttributes.addFlashAttribute("message", "服务器异常，请稍后再试");
            }
        }
        if (StringUtils.isNotBlank(url))
            return "redirect:/login?url=" + url;
        return "redirect:/login";
    }

    /**
     * 注销
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String url, Model model) {
        try {
            //清除cookie中的 token，相当于关闭了全局会话
            CookieUtils.deleteCookie(request, response, "token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotBlank(url)) {
            return "redirect:/login?url=" + url;
        } else {
            return "redirect:/login";
        }
    }
}
