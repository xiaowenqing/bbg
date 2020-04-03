package com.bbg.bizdatapermissionmanager.service.impl;    /**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author xwq
 * @date 2020/3/5 000518:16
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbg.bizdatapermissionmanager.common.dto.DimensionUnitDefineDto;
import com.bbg.bizdatapermissionmanager.dao.DimensionUnitDefineDao;
import com.bbg.bizdatapermissionmanager.entity.DimensionUnitDefineEntity;
import com.bbg.bizdatapermissionmanager.service.DimensionUnitDefineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author xwq
 * @create 2020-03-05 18:16
 **/
@Service
public class DimensionUnitDefineServiceImpl implements DimensionUnitDefineService {

    @Autowired
    private DimensionUnitDefineDao dimensionUnitDefineDao;


    /**
     *
     * 分页查询维度
     * @param page 分页参数
     * @param entity 条件参数
     * @return
     */
    @Override
    public Page<DimensionUnitDefineEntity> selPageList(Page<DimensionUnitDefineEntity> page, DimensionUnitDefineEntity entity) {
        List<DimensionUnitDefineEntity> list = dimensionUnitDefineDao.selPageList(page,entity);

        int count = dimensionUnitDefineDao.count(entity);

        page.setRecords(list);
        page.setTotal(count);
        return page;
    }

    /**
     * 分页查询 根据控制点编码分页查询维度,已经关联isAssociated=1
     * 没有关联isAssociated="0"
     * @param page 分页参数
     * @param entity 条件参数
     * @return
     */
    @Override
    public Page<DimensionUnitDefineDto> selectBpdCodePageList(Page<DimensionUnitDefineDto> page, DimensionUnitDefineDto entity) {
        List<DimensionUnitDefineDto> list = dimensionUnitDefineDao.selectBpdCodePageList(page,entity);

        int count = dimensionUnitDefineDao.countBpdCodePageList(entity);

        page.setRecords(list);
        page.setTotal(count);
        return page;
    }


    /**
     * 根据控制点编码分页查询维度对象
     * @param page 分页参数
     * @param entity 条件参数
     * @return
     */
    @Override
    public Page<DimensionUnitDefineEntity> selectBpdCodePage(Page<DimensionUnitDefineEntity> page, DimensionUnitDefineDto entity) {
        List<DimensionUnitDefineEntity> list = dimensionUnitDefineDao.selectBpdCodePage(page,entity);

        int count = dimensionUnitDefineDao.countBpdCodePage(entity);

        page.setRecords(list);
        page.setTotal(count);
        return page;
    }

    /**
     * 不分页查询
     * @param entity
     * @return
     */
    @Override
    public List<DimensionUnitDefineEntity> selectList(DimensionUnitDefineEntity entity) {
        return dimensionUnitDefineDao.findList(entity);
    }

    /**
     * 新增
     * @param entity
     * @return
     */
    @Override
    public int add(DimensionUnitDefineEntity entity) {
        return dimensionUnitDefineDao.insert(entity);
    }

    /**
     * 修改
     * @param entity
     * @return
     */
    @Override
    public int update(DimensionUnitDefineEntity entity) {
        return dimensionUnitDefineDao.update(entity);
    }

    /**
     * 删除  根据dudCode（维度编码）
     * @param entity
     * @return
     */
    @Override
    public int delete(DimensionUnitDefineEntity entity) {
        return dimensionUnitDefineDao.delete(entity);
    }

    /**
     * 查询单条数据
     * @param entity
     * @return
     */
    @Override
    public DimensionUnitDefineEntity getDimensionUnitDefineEntity(DimensionUnitDefineEntity entity) {
        return dimensionUnitDefineDao.getDimensionUnitDefineEntity(entity);
    }
}
