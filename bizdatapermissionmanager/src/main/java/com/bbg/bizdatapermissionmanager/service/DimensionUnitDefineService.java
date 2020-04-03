package com.bbg.bizdatapermissionmanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbg.bizdatapermissionmanager.common.dto.DimensionUnitDefineDto;
import com.bbg.bizdatapermissionmanager.entity.DimensionUnitDefineEntity;

import java.util.List;

/**
 * @author xwq
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2020/3/5 000518:15
 */
public interface DimensionUnitDefineService {
    /**
     * 分页查询
     * @param page 分页参数
     * @param entity 条件参数
     * @return 当前页数据
     */
    Page<DimensionUnitDefineEntity> selPageList(Page<DimensionUnitDefineEntity> page, DimensionUnitDefineEntity entity);

    /**
     * 分页查询 根据控制点编码分页查询维度,已经关联isAssociated=1
     * 没有关联isAssociated="0"
     * @param page 分页参数
     * @param entity 条件参数
     * @return 当前页数据
     */
    Page<DimensionUnitDefineDto> selectBpdCodePageList(Page<DimensionUnitDefineDto> page, DimensionUnitDefineDto entity);


    /**
     * 根据控制点编码分页查询维度对象
     * @param page 分页参数
     * @param entity 条件参数
     * @return 当前页数据
     */
    Page<DimensionUnitDefineEntity> selectBpdCodePage(Page<DimensionUnitDefineEntity> page, DimensionUnitDefineDto entity);


    /**
     * 不分页查询list
     * @param entity
     * @return
     */
    List<DimensionUnitDefineEntity> selectList(DimensionUnitDefineEntity entity);

    /**
     * 新增
     * @param entity
     */
    int add(DimensionUnitDefineEntity entity);

    /**
     * 修改
     * @param entity
     * @return
     */
    int update(DimensionUnitDefineEntity entity);

    /**
     * 删除  根据dudCode（维度编码）
     * @param entity
     * @return
     */
    int delete(DimensionUnitDefineEntity entity);

    /**
     *  根据参数去查询出一条数据
     *
     *  注意只会查询出一条数据
     * @param entity
     * @return
     */
    DimensionUnitDefineEntity getDimensionUnitDefineEntity(DimensionUnitDefineEntity entity);
}
