package com.nic.ctFire.web.user.controller;

import com.nic.ctFire.common.domain.PageInfo;
import com.nic.ctFire.common.domain.TbUser;
import com.nic.ctFire.common.dto.BaseResult;
import com.nic.ctFire.common.utils.MapperUtils;
import com.nic.ctFire.web.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ModelAttribute("editUser")
    public TbUser tbUser(String id) {
        TbUser editUser = null;
        if (StringUtils.isBlank(id)) {
            editUser = new TbUser();
        } else {
            String json = userService.get(id);
            if (StringUtils.isNotBlank(json)){
                try {
                    BaseResult res = MapperUtils.json2pojo(json, BaseResult.class);
                    editUser = MapperUtils.map2pojo((Map) res.getData(),TbUser.class) ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return editUser;
    }

    @GetMapping(value = {"","main"})
    public String main() {
        return "main";
    }

    @GetMapping(value = "list")
    public String list() {
        return "list";
    }

    @GetMapping(value = "form")
    public String form() {
        return "form";
    }

    @PostMapping(value = "save")
    public String save(TbUser tbUser, RedirectAttributes redirectAttributes) {
        String userJson = null;
        try {
            userJson = MapperUtils.obj2json(tbUser);
            BaseResult baseResult = MapperUtils.json2pojo(userService.save(userJson), BaseResult.class);
            redirectAttributes.addFlashAttribute("baseResult", baseResult);
            // 保存成功
            if (baseResult.getSuccess().endsWith("成功")) {
                return "redirect:/list";
            }
            // 保存失败
            else {
                return "redirect:/form";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @PostMapping(value = "delete")
    public BaseResult delete(String ids) {
        BaseResult res = null;
        try {
             res = MapperUtils.json2pojo(userService.delete(ids),BaseResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public PageInfo<TbUser> page(HttpServletRequest request, TbUser tbUser) {
        PageInfo page = new PageInfo();
        String drawStr = request.getParameter("draw");
        String startStr = request.getParameter("start");
        String lengthStr = request.getParameter("length");
        int draw = drawStr == null ? 0 : Integer.parseInt(drawStr);
        int start = startStr == null ? 0 : Integer.parseInt(startStr);
        int length = lengthStr == null ? 10 : Integer.parseInt(lengthStr);
        try {
            String userJson = MapperUtils.obj2json(tbUser);
            String res = userService.page(start,length,userJson);
            page = MapperUtils.json2pojo(res,PageInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

}
