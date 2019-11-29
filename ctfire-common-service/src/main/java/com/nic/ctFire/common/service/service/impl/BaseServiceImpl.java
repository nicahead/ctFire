package com.nic.ctFire.common.service.service.impl;

import com.nic.ctFire.common.domain.BaseDomain;
import com.nic.ctFire.common.domain.PageInfo;
import com.nic.ctFire.common.service.mapper.BaseMapper;
import com.nic.ctFire.common.service.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseServiceImpl<T extends BaseDomain, D extends BaseMapper<T>> implements BaseService<T> {

    @Autowired
    private D dao;

    @Override
    public int insert(T t) {
        return dao.insert(t);
    }

    @Override
    public int delete(String id) {
        return dao.delete(id);
    }

    @Override
    public int deleteMulti(String[] ids) {
        return dao.deleteMulti(ids);
    }

    @Override
    public int update(T t) {
        return dao.update(t);
    }

    @Override
    public int count(T t) {
        return dao.count(t);
    }

    @Override
    public T selectOne(String id) {
        return dao.getById(id);
    }

    @Override
    public PageInfo<T> page(int start, int length, T t) {
        PageInfo<T> page = new PageInfo();
        int count = dao.count(t);
        page.setData(dao.page(start, length, t));
        page.setRecordsFiltered(count);
        page.setRecordsTotal(count);
        return page;
    }
}
