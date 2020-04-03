package com.bbg.bizdatapermissionmanager.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbg.bizdatapermissionmanager.dao.BizscopeProcessDefineDao;
import com.bbg.bizdatapermissionmanager.entity.BizscopeProcessDefineEntity;
import com.bbg.bizdatapermissionmanager.service.BizscopeProcessDefineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xwq
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2020/3/5 00052:10
 */
@Service
public class BizscopeProcessDefineServiceImpl implements BizscopeProcessDefineService {

    @Autowired
    private BizscopeProcessDefineDao bizscopeProcessDefineDao;

    /**
     *  分页查询
     * @param page 分页参数
     * @param entity 条件参数
     * @return
     */
    @Override
    public Page<BizscopeProcessDefineEntity> selPageList(Page<BizscopeProcessDefineEntity> page, BizscopeProcessDefineEntity entity) {
        List<BizscopeProcessDefineEntity> list=bizscopeProcessDefineDao.selPageList(page,entity);

        int count = bizscopeProcessDefineDao.count(entity);
        page.setRecords(list);
        page.setTotal(count);
        return page;
    }

    /**
     * 不分页查询list
     * @param entity
     * @return
     */
    @Override
    public List<BizscopeProcessDefineEntity> selectList(BizscopeProcessDefineEntity entity) {
        return bizscopeProcessDefineDao.findList(entity);
    }

    /**
     * 新增
     * @param entity
     */
    public int add(BizscopeProcessDefineEntity entity){
       return bizscopeProcessDefineDao.insert(entity);
    }

    @Override
    public int update(BizscopeProcessDefineEntity entity) {

        return bizscopeProcessDefineDao.update(entity);
    }

    /**
     * 根据bpdCode(业务动作编码)删除业务模块
     * @param entity
     * @return
     */
    @Override
    public int delete(BizscopeProcessDefineEntity entity) {

        return bizscopeProcessDefineDao.delete(entity);
    }

    /**
     * 根据参数去查询出一条数据
     *
     *  注意只会查询出一条数据
     * @param entity
     * @return
     */
    @Override
    public BizscopeProcessDefineEntity getBizscopeProcessDefineEntity(BizscopeProcessDefineEntity entity) {
        return bizscopeProcessDefineDao.getBizscopeProcessDefineEntity(entity);
    }

    ;

}
