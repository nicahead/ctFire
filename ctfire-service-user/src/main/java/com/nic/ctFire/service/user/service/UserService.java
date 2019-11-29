package com.nic.ctFire.service.user.service;

import com.nic.ctFire.common.domain.TbUser;
import com.nic.ctFire.common.service.service.BaseService;

public interface UserService extends BaseService<TbUser> {
    TbUser getByName(String username);
}
