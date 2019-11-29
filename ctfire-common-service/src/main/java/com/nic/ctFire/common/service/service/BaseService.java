package com.nic.ctFire.common.service.service;

import com.nic.ctFire.common.domain.BaseDomain;
import com.nic.ctFire.common.domain.PageInfo;

public interface BaseService<T extends BaseDomain> {
    int insert(T t);

    int delete(String id);

    int deleteMulti(String[] ids);

    int update(T t);

    int count(T t);

    T selectOne(String id);

    PageInfo<T> page(int start, int length, T t);
}
