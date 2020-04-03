package com.bbg.bizdatapermissionmanager.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbg.bizdatapermissionmanager.common.dto.DimensionUnitDefineDto;
import com.bbg.bizdatapermissionmanager.entity.DimensionUnitDefineEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author glaive
 * Date  2020-03-05
 */
@Mapper
public interface DimensionUnitDefineDao {

    public DimensionUnitDefineEntity get(String id);

    public DimensionUnitDefineEntity getDimensionUnitDefineEntity(@Param("entity")DimensionUnitDefineEntity entity);


    public List<DimensionUnitDefineEntity> selPageList(@Param("page")Page<DimensionUnitDefineEntity> page, @Param("entity") DimensionUnitDefineEntity entity);

    public Integer count(@Param("entity")DimensionUnitDefineEntity dimensionUnitDefineEntity);

    public List<DimensionUnitDefineDto> selectBpdCodePageList(@Param("page")Page<DimensionUnitDefineDto> page, @Param("entity") DimensionUnitDefineDto entity);

    public Integer countBpdCodePageList(@Param("entity")DimensionUnitDefineDto entity);

    public List<DimensionUnitDefineEntity> selectBpdCodePage(@Param("page")Page<DimensionUnitDefineEntity> page, @Param("entity") DimensionUnitDefineDto entity);

    public Integer countBpdCodePage(@Param("entity")DimensionUnitDefineDto entity);


    public List<DimensionUnitDefineEntity> findList(@Param("entity")DimensionUnitDefineEntity entity);

    public List<DimensionUnitDefineEntity> findAllList();

    public int insert(DimensionUnitDefineEntity dimensionUnitDefineEntity);

    public int insertBatch(List<DimensionUnitDefineEntity> dimensionUnitDefineEntitys);

    public int update(DimensionUnitDefineEntity dimensionUnitDefineEntity);

    public int delete(DimensionUnitDefineEntity dimensionUnitDefineEntity);

}