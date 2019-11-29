package com.nic.ctFire.service.sso.service.impl;

import com.nic.ctFire.common.domain.TbUser;
import com.nic.ctFire.common.service.mapper.TbUserMapper;
import com.nic.ctFire.common.utils.MapperUtils;
import com.nic.ctFire.service.sso.service.LoginService;
import com.nic.ctFire.service.sso.service.consumer.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser login(String username, String password) {
        TbUser tbUser = null;
        String json = redisService.get(username);
        //缓存中没有登录的用户信息,则到数据库中查询
        String md5pwd = DigestUtils.md5DigestAsHex(password.getBytes());
        if (json == null) {
            tbUser = tbUserMapper.getByName(username);
            if (tbUser != null && tbUser.getPassword().equals(md5pwd)) {
                try {
                    tbUser.setLogined(new Date());
                    tbUserMapper.update(tbUser);
                    //登录成功存到redis
                    redisService.put(username, MapperUtils.obj2json(tbUser), 60 * 60 * 24);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return null;
            }
        }
        //缓存中有登录的用户信息，直接返回
        else {
            try {
                tbUser = MapperUtils.json2pojo(json, TbUser.class);
                if (tbUser == null || !tbUser.getPassword().equals(md5pwd))
                    return null;
            } catch (Exception e) {
                logger.warn("触发熔断：{}", e.getMessage());
            }
        }
        return tbUser;
    }
}
