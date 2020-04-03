package com.bbg.bizdatapermissionmanager.service.impl;    /**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author xwq
 * @date 2020/3/5 000522:41
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbg.bizdatapermissionmanager.common.dto.ProcessDimensionRelDto;
import com.bbg.bizdatapermissionmanager.common.dto.RolepermissionRelReq;
import com.bbg.bizdatapermissionmanager.common.enums.StatusEnums;
import com.bbg.bizdatapermissionmanager.dao.BizscopeProcessDefineDao;
import com.bbg.bizdatapermissionmanager.dao.DimensionUnitDefineDao;
import com.bbg.bizdatapermissionmanager.dao.ProcessDimensionRelDao;
import com.bbg.bizdatapermissionmanager.dao.RolepermissionRelDao;
import com.bbg.bizdatapermissionmanager.entity.BizscopeProcessDefineEntity;
import com.bbg.bizdatapermissionmanager.entity.DimensionUnitDefineEntity;
import com.bbg.bizdatapermissionmanager.entity.ProcessDimensionRelEntity;
import com.bbg.bizdatapermissionmanager.entity.RolepermissionRelEntity;
import com.bbg.bizdatapermissionmanager.service.ProcessDimensionRelService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 *
 * @author xwq
 * @create 2020-03-05 22:41
 **/
@Service
@Transactional
public class ProcessDimensionRelServiceImpl  implements ProcessDimensionRelService{

    @Autowired
    private ProcessDimensionRelDao processDimensionRelDao;

    @Autowired
    private RolepermissionRelDao rolepermissionRelDao;

    @Autowired
    private DimensionUnitDefineDao dimensionUnitDefineDao;

    @Autowired
    private BizscopeProcessDefineDao bizscopeProcessDefineDao;


    /**
     * 分页查询
     * @param page 分页参数
     * @param entity 条件参数
     * @return 当前页数据
     */
    @Override
    public Page<ProcessDimensionRelEntity> selPageList(Page<ProcessDimensionRelEntity> page, ProcessDimensionRelEntity entity) {
        List<ProcessDimensionRelEntity> list = processDimensionRelDao.selPageList(page,entity);

        int count = processDimensionRelDao.count(entity);
        page.setRecords(list);
        page.setTotal(count);
        return page;
    }

    /**
     * 分页查询 数据资源对象，根据参数roleId(角色编码)，去查询出对应的资源，
     * 并且如果该数据资源和角色已经关联isAssociated="1"
     * 没有关联isAssociated="0"
     * @param page 分页参数
     * @param entity 条件参数
     * @return 当前页数据
     */
    @Override
    public Page<ProcessDimensionRelDto> selectRoleIdPage(Page<ProcessDimensionRelDto> page, ProcessDimensionRelDto entity) {
        List<ProcessDimensionRelDto> list = processDimensionRelDao.selectRoleIdPage(page,entity);

        int count = processDimensionRelDao.countRoleId(entity);
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
    public List<ProcessDimensionRelEntity> selectList(ProcessDimensionRelEntity entity) {
        return processDimensionRelDao.findList(entity);
    }

    /**
     * 新增
     * @param entity
     * @return
     */
    @Override
    public int add(ProcessDimensionRelEntity entity) {
        return processDimensionRelDao.insert(entity);
    }

    /**
     * 批量新增
     * @param list
     * @return
     */
    @Override
    public int addList(List<ProcessDimensionRelEntity> list) {
        return processDimensionRelDao.insertBatch(list);
    }


    /**
     * 修改
     * @param entity
     * @return
     */
    @Override
    public int update(ProcessDimensionRelEntity entity) {
        return processDimensionRelDao.update(entity);
    }

    /**
     * 批量修改
     * @param list
     * @return
     */
    @Override
    public int updateBatch(List<ProcessDimensionRelEntity> list) {

        //先根据bpdCode(控制点编码)和dudCode(维度编码)来查询出数据库中的数据
        ProcessDimensionRelEntity entity = new ProcessDimensionRelEntity();
        entity.setBpdCode(list.get(0).getBpdCode());
        entity.setDudCode(list.get(0).getDudCode());
        entity.setPermitType(StatusEnums.FINISHED.getCode());//查询允许类型的
        List<ProcessDimensionRelEntity> listDB =  processDimensionRelDao.findList(entity);

        //因为bpdCode(控制点编码)和dudCode(维度编码)相同，数据不同的是permitValue(许可值)
        List<String> permitValueList = list.stream().map(ProcessDimensionRelEntity :: getPermitValue)
                .collect(toList());//传递的参数
        List<String> permitValueListDB = listDB.stream().map(ProcessDimensionRelEntity :: getPermitValue)
                .collect(toList());//数据库中查询结果


        //取俩集合的交集
        List<String> intersection = permitValueList.stream().filter(item -> permitValueListDB.contains(item)).collect(toList());

        //permitValueList(传递的参数) - intersection(交集) = addList(需要新增的集合)
        List<String> addList = permitValueList.stream().filter(item -> !intersection.contains(item)).collect(toList());

        //permitValueListDB(数据库中查询结果) - intersection(交集) = deleteList(需要删除的集合)
        List<String> deleteList = permitValueListDB.stream().filter(item -> !intersection.contains(item)).collect(toList());

        //根据判断的集合，分别获取到需要新增和删除的实体类
        List<ProcessDimensionRelEntity> addEntityList = new ArrayList<>();//新增list
        List<ProcessDimensionRelEntity> deleteEntityList = new ArrayList<>();//删除list

        if(addList.size() > 0 ){
            for(String permitValue : addList){
                for(ProcessDimensionRelEntity pdrEntity: list){
                    //去参数list中对比，查询出需要新增的集合
                    if(pdrEntity.getPermitValue().equals(permitValue)){
                        addEntityList.add(pdrEntity);
                    }
                }
            }
        }

        if(deleteList.size() > 0){
            for(String permitValue : deleteList){
                for(ProcessDimensionRelEntity pdrEntity : listDB){
                    //去数据库中查询出的集合中对比出需要删除的集合
                    if(pdrEntity.getPermitValue().equals(permitValue)){
                        deleteEntityList.add(pdrEntity);
                    }
                }
            }
        }

        if(addEntityList.size()>0){
            processDimensionRelDao.insertBatch(addEntityList);
        }

        if(deleteEntityList.size() >0){
            List<Integer> pdrIds = deleteEntityList.stream().map(ProcessDimensionRelEntity :: getPdrId)
                    .collect(toList());

            // list<Integer> -> Integer[]
            Integer[] ids = pdrIds.toArray(new Integer[0]);

            //批量删除
            processDimensionRelDao.deleteBatch(ids);
        }


        return 1;
    }

    /**
     * 根据维度批量更新操作
     * @param list
     * @return
     */
    @Override
    public int updateDudCodeBatch(List<ProcessDimensionRelEntity> list) {

        //第一步，查询出维度关联的控制点
        List<BizscopeProcessDefineEntity> bizscopeProcessDefineEntityList = bizscopeProcessDefineDao.selectDudCode(list.get(0).getDudCode());

        if(bizscopeProcessDefineEntityList.size() >0 ){
            //根据判断的集合，分别获取到需要新增和删除的实体类
            List<ProcessDimensionRelEntity> addEntityList = new ArrayList<>();//新增list
            List<ProcessDimensionRelEntity> deleteEntityList = new ArrayList<>();//删除list


            for(BizscopeProcessDefineEntity bizscopeProcessDefineEntity : bizscopeProcessDefineEntityList){

                //先根据bpdCode(控制点编码)和dudCode(维度编码)来查询出数据库中的数据
                ProcessDimensionRelEntity entity = new ProcessDimensionRelEntity();
                entity.setBpdCode(bizscopeProcessDefineEntity.getBpdCode());
                entity.setDudCode(list.get(0).getDudCode());
                entity.setPermitType(StatusEnums.FINISHED.getCode());
                List<ProcessDimensionRelEntity> listDB =  processDimensionRelDao.findList(entity);


                //数据不同的是permitValue(许可值)
                List<String> permitValueList = list.stream().map(ProcessDimensionRelEntity :: getPermitValue)
                        .collect(toList());//传递的参数
                List<String> permitValueListDB = listDB.stream().map(ProcessDimensionRelEntity :: getPermitValue)
                        .collect(toList());//数据库中查询结果

                //取俩集合的交集
                List<String> intersection = permitValueList.stream().filter(item -> permitValueListDB.contains(item)).collect(toList());

                //permitValueList(传递的参数) - intersection(交集) = addList(需要新增的集合)
                List<String> addList = permitValueList.stream().filter(item -> !intersection.contains(item)).collect(toList());

                //permitValueListDB(数据库中查询结果) - intersection(交集) = deleteList(需要删除的集合)
                List<String> deleteList = permitValueListDB.stream().filter(item -> !intersection.contains(item)).collect(toList());

                if(addList.size() > 0 ){
                    for(String permitValue : addList){
                        for(ProcessDimensionRelEntity pdrEntity: list){
                            //去参数list中对比，查询出需要新增的集合
                            if(pdrEntity.getPermitValue().equals(permitValue)){
                                pdrEntity.setCreateTime(new Date());//新增时间
                                pdrEntity.setLastmodifiTime(new Date());//修改时间
                                pdrEntity.setBpdCode(bizscopeProcessDefineEntity.getBpdCode());//控制点编码
                                addEntityList.add(pdrEntity);
                            }
                        }
                    }
                }

                if(deleteList.size() > 0){
                    for(String permitValue : deleteList){
                        for(ProcessDimensionRelEntity pdrEntity : listDB){
                            //去数据库中查询出的集合中对比出需要删除的集合
                            if(pdrEntity.getPermitValue().equals(permitValue)){
                                deleteEntityList.add(pdrEntity);
                            }
                        }
                    }
                }

            }


            if(addEntityList.size()>0){
                processDimensionRelDao.insertBatch(addEntityList);
            }

            if(deleteEntityList.size() >0){
                List<Integer> pdrIds = deleteEntityList.stream().map(ProcessDimensionRelEntity :: getPdrId)
                        .collect(toList());

                // list<Integer> -> Integer[]
                Integer[] ids = pdrIds.toArray(new Integer[0]);

                //批量删除
                processDimensionRelDao.deleteBatch(ids);
            }

        }
        return 1;
    }

    /**
     * 删除
     * @param entity
     * @return
     */
    @Override
    public int delete(ProcessDimensionRelEntity entity) {

        int count = processDimensionRelDao.delete(entity);

        //需要去删除掉和角色的关联数据
        RolepermissionRelEntity rolepermissionRelEntity = new RolepermissionRelEntity();
        rolepermissionRelEntity.setPdrId(entity.getPdrId());

        int count1 = rolepermissionRelDao.deletePdrId(rolepermissionRelEntity);

        return count;
    }

    /**
     * 批量删除
     * @param pdrIds
     * @return
     */
    @Override
    public int deleteBatch(Integer[] pdrIds) {
        //Integer[] -> list<Integer>
        List<Integer> pdrIdList  = new ArrayList<>(Arrays.asList(pdrIds));

        if(pdrIdList.size()>0){
            rolepermissionRelDao.deleteBatchPdrId(pdrIdList);
        }


        int count = processDimensionRelDao.deleteBatch(pdrIds);
        return count;
    }

    /**
     * 根据bpdCode(控制点编码)删除
     * @param entity
     * @return
     */
    @Override
    public int deleteBpdCode(ProcessDimensionRelEntity entity) {
        //查询所有的资源
        List<ProcessDimensionRelEntity>  list = processDimensionRelDao.findList(entity);

        List<Integer> pdrIdList = list.stream().map(ProcessDimensionRelEntity :: getPdrId)
                .collect(toList());


        if(pdrIdList.size()>0){
            rolepermissionRelDao.deleteBatchPdrId(pdrIdList);
        }


        int count = processDimensionRelDao.deleteBpdCode(entity);



        return count;
    }


    /**
     * 根据bpdCode(控制点编码)+dudCode(维度编码)删除
     * @param entity
     * @return
     */
    @Override
    public int deleteBpdCodeAndDudCode(ProcessDimensionRelEntity entity) {

        return processDimensionRelDao.deleteBpdCodeAndDudCode(entity);
    }

    /**
     * 查询单条
     * @param entity
     * @return
     */
    @Override
    public ProcessDimensionRelEntity getProcessDimensionRelEntity(ProcessDimensionRelEntity entity) {
        return processDimensionRelDao.getProcessDimensionRelEntity(entity);
    }


    /**
     * 根据角色id集合+bpdCode(控制点编码)+dudCode(维度编码)查询出数据资源关联集合
     * @param rrReq
     * @return
     */
    @Override
    public List<ProcessDimensionRelEntity> selectRoleIds(RolepermissionRelReq rrReq) {

        List<String> dudCodeList = new ArrayList<>();

        if(!StringUtils.isEmpty(rrReq.getDudCode())){
            //说明dudCode(维度编码)有值，需要去判断维度是否是父维度
            DimensionUnitDefineEntity dudEntity = new DimensionUnitDefineEntity();
            dudEntity.setDudCode(rrReq.getDudCode());

            dudEntity = dimensionUnitDefineDao.getDimensionUnitDefineEntity(dudEntity);

            if(StatusEnums.NEW.getCode().equals(dudEntity.getDudIseffective())){
                //dudIseffective = "0" 不是最终审核维度,就需要去查询对应的子维度
                DimensionUnitDefineEntity dud = new DimensionUnitDefineEntity();
                dud.setDudPcode(dudEntity.getDudCode());
                dud.setStatus(StatusEnums.FINISHED.getCode());
                //查询出对应的有效的子维度
                List<DimensionUnitDefineEntity> dudList = dimensionUnitDefineDao.findList(dud);

                if(dudList.size() > 0){
                    dudCodeList = dudList.stream().map(DimensionUnitDefineEntity :: getDudCode)
                            .collect(toList());
                }

            }else{
                dudCodeList.add(rrReq.getDudCode());
            }
        }

        rrReq.setDudCodeList(dudCodeList);

        List<ProcessDimensionRelEntity> list = processDimensionRelDao.selectRoleIds(rrReq);

        return list;
    }
}
