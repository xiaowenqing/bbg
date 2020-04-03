package com.bbg.bizdatapermissionmanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbg.bizdatapermissionmanager.common.dto.ProcessDimensionRelDto;
import com.bbg.bizdatapermissionmanager.common.dto.RolepermissionRelReq;
import com.bbg.bizdatapermissionmanager.entity.ProcessDimensionRelEntity;

import java.util.List;

/**
 * @author xwq
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2020/3/5 000522:40
 */
public interface ProcessDimensionRelService {


    /**
     * 分页查询
     * @param page 分页参数
     * @param entity 条件参数
     * @return 当前页数据
     */
    Page<ProcessDimensionRelEntity> selPageList(Page<ProcessDimensionRelEntity> page, ProcessDimensionRelEntity entity);



    /**
     * 分页查询 数据资源对象，根据参数roleId(角色编码)，去查询出对应的资源，
     * 并且如果该数据资源和角色已经关联isAssociated="1"
     * 没有关联isAssociated="0"
     * @param page 分页参数
     * @param entity 条件参数
     * @return 当前页数据
     */
    Page<ProcessDimensionRelDto> selectRoleIdPage(Page<ProcessDimensionRelDto> page, ProcessDimensionRelDto entity);




    /**
     * 不分页查询list
     * @param entity
     * @return
     */
    List<ProcessDimensionRelEntity> selectList(ProcessDimensionRelEntity entity);

    /**
     * 新增
     * @param entity
     */
    int add(ProcessDimensionRelEntity entity);


    /**
     * 批量新增
     * @param list
     */
    int addList(List<ProcessDimensionRelEntity> list);


    /**
     * 修改
     * @param entity
     * @return
     */
    int update(ProcessDimensionRelEntity entity);


    /**
     * 批量修改
     * @param list
     * @return
     */
    int updateBatch(List<ProcessDimensionRelEntity> list);

    /**
     * 根据维度批量更新操作
     * @param list
     * @return
     */
    int updateDudCodeBatch(List<ProcessDimensionRelEntity> list);


    /**
     * 删除  根据主键pdrId删除
     * @param entity
     * @return
     */
    int delete(ProcessDimensionRelEntity entity);

    /**
     * 批量删除  根据主键pdrId批量删除
     * @param pdrIds
     * @return
     */
    int deleteBatch(Integer[] pdrIds);

    /**
     * 根据bpdCode(控制点编码)删除
     * @param entity
     * @return
     */
    int deleteBpdCode(ProcessDimensionRelEntity entity);

    /**
     * 根据bpdCode(控制点编码)+dudCode(维度编码)删除
     * @param entity
     * @return
     */
    int deleteBpdCodeAndDudCode(ProcessDimensionRelEntity entity);

    /**
     *  根据参数去查询出一条数据
     *
     *  注意只会查询出一条数据
     * @param entity
     * @return
     */
    ProcessDimensionRelEntity getProcessDimensionRelEntity(ProcessDimensionRelEntity entity);

    /**
     * 根据角色id集合+bpdCode(控制点编码)+dudCode(维度编码)查询出数据资源关联集合
     * @param rrReq
     * @return
     */
    List<ProcessDimensionRelEntity> selectRoleIds(RolepermissionRelReq rrReq);


}
