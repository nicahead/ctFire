package com.nic.ctFire.service.user.service.impl;

import com.nic.ctFire.common.domain.TbUser;
import com.nic.ctFire.common.service.mapper.TbUserMapper;
import com.nic.ctFire.common.service.service.impl.BaseServiceImpl;
import com.nic.ctFire.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends BaseServiceImpl<TbUser, TbUserMapper> implements UserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser getByName(String username) {
        return tbUserMapper.getByName(username);
    }
}
