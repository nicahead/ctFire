package com.nic.ctFire.common.service.mapper;

import com.nic.ctFire.common.domain.BaseDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<T extends BaseDomain> {

    /**
     * 新增
     *
     * @param entity
     */
    int insert(T entity);

    /**
     * 删除
     *
     * @param id
     */
    int delete(String id);

    /**
     * 批量删除
     *
     * @param ids
     */
    int deleteMulti(String[] ids);

    /**
     * 更新
     *
     * @param entity
     */
    int update(T entity);

    /**
     * 根据 ID 查询信息
     *
     * @param id
     * @return
     */
    T getById(String id);

    /**
     * 查询全部数据
     *
     * @return
     */
    List<T> selectAll();

    /**
     * 分页查询
     *
     * @param
     * @return
     */
    List<T> page(@Param("start") int start,@Param("length") int length,@Param("entity") T entity);

    /**
     * 查询总笔数
     *
     * @return
     */
    int count(@Param("entity") T entity);
}
