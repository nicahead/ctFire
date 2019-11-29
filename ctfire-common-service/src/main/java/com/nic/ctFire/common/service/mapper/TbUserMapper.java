package com.nic.ctFire.common.service.mapper;

import com.nic.ctFire.common.domain.TbUser;
import com.nic.ctFire.common.service.utils.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

public interface TbUserMapper extends BaseMapper<TbUser> {

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    TbUser getByName(String username);
}
