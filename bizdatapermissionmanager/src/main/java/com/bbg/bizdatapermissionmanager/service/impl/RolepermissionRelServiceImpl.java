package com.bbg.bizdatapermissionmanager.service.impl;    /**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author xwq
 * @date 2020/3/7 000715:34
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbg.bizdatapermissionmanager.dao.RolepermissionRelDao;
import com.bbg.bizdatapermissionmanager.entity.RolepermissionRelEntity;
import com.bbg.bizdatapermissionmanager.service.RolepermissionRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author xwq
 * @create 2020-03-07 15:34
 **/
@Service
public class RolepermissionRelServiceImpl implements RolepermissionRelService {

    @Autowired
    private RolepermissionRelDao rolepermissionRelDao;


    /**
     * 分页查询
     * @param page 分页参数
     * @param entity 条件参数
     * @return
     */
    @Override
    public Page<RolepermissionRelEntity> selPageList(Page<RolepermissionRelEntity> page, RolepermissionRelEntity entity) {
        List<RolepermissionRelEntity> list =rolepermissionRelDao.selPageList(page,entity);

        int count = rolepermissionRelDao.count(entity);

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
    public List<RolepermissionRelEntity> selectList(RolepermissionRelEntity entity) {
        return rolepermissionRelDao.findList(entity);
    }

    /**
     * 新增
     * @param entity
     * @return
     */
    @Override
    public int add(RolepermissionRelEntity entity) {
        return rolepermissionRelDao.insert(entity);
    }

    /**
     * 批量新增
     * @param list
     * @return
     */
    @Override
    public int addList(List<RolepermissionRelEntity> list) {
        return rolepermissionRelDao.insertBatch(list);
    }

    /**
     * 修改
     * @param entity
     * @return
     */
    @Override
    public int update(RolepermissionRelEntity entity) {
        return rolepermissionRelDao.update(entity);
    }

    /**
     * 根据主键删除
     * @param entity
     * @return
     */
    @Override
    public int delete(RolepermissionRelEntity entity) {
        return rolepermissionRelDao.delete(entity);
    }

    /**
     * 查询单条
     * @param entity
     * @return
     */
    @Override
    public RolepermissionRelEntity getRolepermissionRelEntity(RolepermissionRelEntity entity) {
        return rolepermissionRelDao.getRolepermissionRelEntity(entity);
    }



}
