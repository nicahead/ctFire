package com.nic.ctFire.common.service.mapper;

import com.nic.ctFire.common.domain.TbDevice;
import com.nic.ctFire.common.service.utils.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

@CacheNamespace(implementation = RedisCache.class)
public interface TbDeviceMapper extends BaseMapper<TbDevice> {
}
