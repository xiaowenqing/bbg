package com.bbg.bizdatapermissionmanager.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbg.bizdatapermissionmanager.common.dto.ProcessDimensionRelDto;
import com.bbg.bizdatapermissionmanager.common.dto.RolepermissionRelReq;
import com.bbg.bizdatapermissionmanager.entity.ProcessDimensionRelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author glaive
 * Date  2020-03-05
 */
@Mapper
public interface ProcessDimensionRelDao {

    public ProcessDimensionRelEntity get(String id);

    public ProcessDimensionRelEntity getProcessDimensionRelEntity(@Param("entity")ProcessDimensionRelEntity entity);


    public List<ProcessDimensionRelEntity> selPageList(@Param("page")Page<ProcessDimensionRelEntity> page, @Param("entity") ProcessDimensionRelEntity entity);

    public Integer count(@Param("entity")ProcessDimensionRelEntity entity);


    public List<ProcessDimensionRelDto> selectRoleIdPage(@Param("page")Page<ProcessDimensionRelDto> page, @Param("entity") ProcessDimensionRelDto entity);

    public Integer countRoleId(@Param("entity")ProcessDimensionRelDto entity);



    public List<ProcessDimensionRelEntity> findList(@Param("entity")ProcessDimensionRelEntity entity);

    public List<ProcessDimensionRelEntity> selectRoleIds(@Param("entity")RolepermissionRelReq entity);

    public List<ProcessDimensionRelEntity> findAllList();

    public int insert(ProcessDimensionRelEntity processDimensionRelEntity);

    public int insertBatch(List<ProcessDimensionRelEntity> processDimensionRelEntitys);

    public int update(ProcessDimensionRelEntity processDimensionRelEntity);

    public int delete(ProcessDimensionRelEntity processDimensionRelEntity);

    public int deleteBatch(Integer[] pdrIds);

    public int deleteBpdCode(ProcessDimensionRelEntity processDimensionRelEntity);

    public int deleteBpdCodeAndDudCode(ProcessDimensionRelEntity processDimensionRelEntity);


}