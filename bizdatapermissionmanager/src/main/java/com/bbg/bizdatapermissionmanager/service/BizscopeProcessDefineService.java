package com.bbg.bizdatapermissionmanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbg.bizdatapermissionmanager.entity.BizscopeProcessDefineEntity;

import java.util.List;

/**
 * @author xwq
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2020/3/5 00052:10
 */
public interface BizscopeProcessDefineService {

    /**
     * 分页查询
     * @param page 分页参数
     * @param entity 条件参数
     * @return 当前页数据
     */
    Page<BizscopeProcessDefineEntity> selPageList(Page<BizscopeProcessDefineEntity> page, BizscopeProcessDefineEntity entity);

    /**
     * 不分页查询list
     * @param entity
     * @return
     */
    List<BizscopeProcessDefineEntity> selectList(BizscopeProcessDefineEntity entity);

    /**
     * 新增
     * @param entity
     */
    int add(BizscopeProcessDefineEntity entity);

    /**
     * 修改
     * @param entity
     * @return
     */
    int update(BizscopeProcessDefineEntity entity);

    /**
     * 根据bpdCode(业务动作编码)删除业务模块
     * @param entity
     * @return
     */
    int delete(BizscopeProcessDefineEntity entity);

    /**
     *  根据参数去查询出一条数据
     *
     *  注意只会查询出一条数据
     * @param entity
     * @return
     */
    BizscopeProcessDefineEntity getBizscopeProcessDefineEntity(BizscopeProcessDefineEntity entity);
}
