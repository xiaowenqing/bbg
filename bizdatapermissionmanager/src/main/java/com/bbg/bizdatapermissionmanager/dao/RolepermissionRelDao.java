package com.bbg.bizdatapermissionmanager.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbg.bizdatapermissionmanager.entity.RolepermissionRelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author glaive
 * Date  2020-03-05
 */
@Mapper
public interface RolepermissionRelDao {

    public RolepermissionRelEntity get(String id);

    public RolepermissionRelEntity getRolepermissionRelEntity(@Param("entity")RolepermissionRelEntity entity);


    public List<RolepermissionRelEntity> selPageList(@Param("page")Page<RolepermissionRelEntity> page, @Param("entity") RolepermissionRelEntity entity);

    public Integer count(@Param("entity")RolepermissionRelEntity entity);


    public List<RolepermissionRelEntity> findList(@Param("entity")RolepermissionRelEntity entity);

    public List<RolepermissionRelEntity> findAllList();

    public int insert(RolepermissionRelEntity rolepermissionRelEntity);

    public int insertBatch(List<RolepermissionRelEntity> rolepermissionRelEntitys);

    public int update(RolepermissionRelEntity rolepermissionRelEntity);

    public int delete(RolepermissionRelEntity rolepermissionRelEntity);

    /**
     *  根据roleId(角色编码)删除
     * @param rolepermissionRelEntity
     * @return
     */
    public int deleteRoleId(RolepermissionRelEntity rolepermissionRelEntity);

    /**
     * 根据pdrId许可资源编码删除
     * @param rolepermissionRelEntity
     * @return
     */
    public int deletePdrId(RolepermissionRelEntity rolepermissionRelEntity);


    /**
     * 批量删除     根据pdrId许可资源编码删除
     * @param pdrId
     * @return
     */
    public int deleteBatchPdrId(List<Integer> pdrId);

}