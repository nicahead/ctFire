package com.nic.ctFire.service.sso.service;

import com.nic.ctFire.common.domain.TbUser;

public interface LoginService {

    public TbUser login(String username,String password);
}
