package com.bbg.bizdatapermissionmanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbg.bizdatapermissionmanager.entity.RolepermissionRelEntity;

import java.util.List;

/**
 * @author xwq
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2020/3/7 000715:33
 */
public interface RolepermissionRelService {

    /**
     * 分页查询
     * @param page 分页参数
     * @param entity 条件参数
     * @return 当前页数据
     */
    Page<RolepermissionRelEntity> selPageList(Page<RolepermissionRelEntity> page, RolepermissionRelEntity entity);

    /**
     * 不分页查询list
     * @param entity
     * @return
     */
    List<RolepermissionRelEntity> selectList(RolepermissionRelEntity entity);

    /**
     * 新增
     * @param entity
     */
    int add(RolepermissionRelEntity entity);


    /**
     * 批量新增
     * @param list
     */
    int addList(List<RolepermissionRelEntity> list);


    /**
     * 修改
     * @param entity
     * @return
     */
    int update(RolepermissionRelEntity entity);

    /**
     * 删除  根据主键pdrId删除
     * @param entity
     * @return
     */
    int delete(RolepermissionRelEntity entity);



    /**
     *  根据参数去查询出一条数据
     *
     *  注意只会查询出一条数据
     * @param entity
     * @return
     */
    RolepermissionRelEntity getRolepermissionRelEntity(RolepermissionRelEntity entity);



}
