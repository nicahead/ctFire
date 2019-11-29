package com.nic.ctFire.service.user.controller;

import com.nic.ctFire.common.domain.PageInfo;
import com.nic.ctFire.common.domain.TbUser;
import com.nic.ctFire.common.dto.BaseResult;
import com.nic.ctFire.common.utils.MapperUtils;
import com.nic.ctFire.service.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "save")
    public BaseResult save(@RequestParam(required = true) String userJson) {
        int result = 0;
        TbUser tbUser = null;
        try {
            tbUser = MapperUtils.json2pojo(userJson, TbUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tbUser != null) {
            BaseResult baseResult = check(tbUser);
            if (baseResult.getSuccess().equals("ok")){
                // 密码加密
                if (StringUtils.isNotBlank(tbUser.getPassword())){
                    String password = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
                    tbUser.setPassword(password);
                }
                // 新增用户
                if (StringUtils.isBlank(tbUser.getId())) {
                    if (StringUtils.isBlank(tbUser.getPassword())){
                        baseResult.setSuccess("密码不能为空");
                        return baseResult;
                    }
                    if (userService.getByName(tbUser.getUsername())!=null){
                        baseResult.setSuccess("用户名已存在");
                        return baseResult;
                    }
                    tbUser.setId(UUID.randomUUID().toString());
                    tbUser.setUpdated(new Date());
                    result = userService.insert(tbUser);
                }
                // 修改用户
                else {
                    result = userService.update(tbUser);
                }
                // 最少有一行数据受影响
                if (result > 0) {
                    return BaseResult.ok("保存用户成功");
                }
            }
            return baseResult;
        }
        return BaseResult.ok("保存用户失败");
    }

    @PostMapping(value = "delete")
    public BaseResult delete(@RequestParam(required = true) String ids) {
        int result = 0;
        if (!StringUtils.isBlank(ids)){
            String[] idArray = ids.split(",");
            if (idArray.length>1)
                result = userService.deleteMulti(idArray);
            else
                result = userService.delete(idArray[0]);
        }
        if (result > 0)
            return BaseResult.ok("删除成功");
        return BaseResult.ok("删除失败");
    }

    @GetMapping(value = "get")
    public BaseResult get(@RequestParam(required = true) String id) {
        TbUser tbUser = userService.selectOne(id);
        if (tbUser != null)
            return BaseResult.ok(tbUser);
        return BaseResult.ok("没有查询到该用户");
    }

    @GetMapping(value = "page")
    public PageInfo<TbUser> page(@RequestParam(required = true) int start,
                                 @RequestParam(required = true) int length,
                                 @RequestParam(required = true) String userJson) {
        TbUser tbUser = null;
        try {
            tbUser = MapperUtils.json2pojo(userJson, TbUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userService.page(start, length, tbUser);
    }

    private BaseResult check(TbUser tbUser){
        BaseResult baseResult = new BaseResult();
        if (StringUtils.isBlank(tbUser.getUsername())){
            baseResult.setSuccess("用户名不能为空");
            return baseResult;
        }
        if (StringUtils.isBlank(tbUser.getEmail())){
            baseResult.setSuccess("邮箱不能为空");
            return baseResult;
        }
        baseResult.setSuccess("ok");
        return baseResult;
    }
}
