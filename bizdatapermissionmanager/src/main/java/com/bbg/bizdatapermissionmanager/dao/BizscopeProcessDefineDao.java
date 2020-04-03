package com.bbg.bizdatapermissionmanager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbg.bizdatapermissionmanager.entity.BizscopeProcessDefineEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xwq
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2020/3/4 000423:37
 */
@Mapper
@Repository
public interface BizscopeProcessDefineDao extends BaseMapper<BizscopeProcessDefineEntity> {

    public BizscopeProcessDefineEntity get(String id);

    public BizscopeProcessDefineEntity getBizscopeProcessDefineEntity(BizscopeProcessDefineEntity bizscopeProcessDefineEntity);

    public List<BizscopeProcessDefineEntity> selPageList(@Param("page")Page<BizscopeProcessDefineEntity> page,@Param("entity") BizscopeProcessDefineEntity entity);

    public Integer count(@Param("entity")BizscopeProcessDefineEntity bizscopeProcessDefineEntity);

    public List<BizscopeProcessDefineEntity> findList(@Param("entity")BizscopeProcessDefineEntity entity);

    public List<BizscopeProcessDefineEntity> findAllList();

    public int insert(BizscopeProcessDefineEntity bizscopeProcessDefine);

    public int insertBatch(List<BizscopeProcessDefineEntity> bizscopeProcessDefines);

    public int update(BizscopeProcessDefineEntity bizscopeProcessDefine);

    public int delete(BizscopeProcessDefineEntity bizscopeProcessDefine);


    /**
     * 根据维度编码dudCode去关联表中查询出对应的控制点有哪些
     * @param dudCode
     * @return
     */
    public List<BizscopeProcessDefineEntity> selectDudCode(@Param("dudCode")String dudCode);

}
