package com.bbg.bizdatapermissionmanager.controller;    /**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author xwq
 * @date 2020/3/5 000522:37
 */


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbg.bizdatapermissionmanager.common.dto.ListWrapper;
import com.bbg.bizdatapermissionmanager.common.dto.ProcessDimensionRelDto;
import com.bbg.bizdatapermissionmanager.common.dto.RolepermissionRelReq;
import com.bbg.bizdatapermissionmanager.common.enums.StatusEnums;
import com.bbg.bizdatapermissionmanager.common.enums.TenantIdEnums;
import com.bbg.bizdatapermissionmanager.entity.*;
import com.bbg.bizdatapermissionmanager.entity.ProcessDimensionRelEntity;
import com.bbg.bizdatapermissionmanager.service.BizscopeProcessDefineService;
import com.bbg.bizdatapermissionmanager.service.DimensionUnitDefineService;
import com.bbg.bizdatapermissionmanager.service.ProcessDimensionRelService;
import com.cloud.domain.response.CodeConstants;
import com.cloud.domain.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


/**
 *操作与元素关系表 控制类
 * @author xwq
 * @create 2020-03-05 22:37
 **/
@RestController
@Api(tags = "操作与元素关系表控制类")
@RequestMapping(value = "/processDimensionRel")
@Slf4j
public class ProcessDimensionRelController {

    @Autowired
    private ProcessDimensionRelService processDimensionRelService;

    @Autowired
    private BizscopeProcessDefineService bizscopeProcessDefineService;

    @Autowired
    private DimensionUnitDefineService dimensionUnitDefineService;

    @ApiOperation(value = "分页查询操作与元素关系", notes = "分页查询操作与元素关系", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/page", produces = "application/json;charset=UTF-8")
    public CommonResponse page(@RequestBody ProcessDimensionRelDto entity) {

        if (entity.getCurrentPage() == null || entity.getPageSize() == null) {
            entity.setCurrentPage(1);
            entity.setPageSize(10);
        }

        Page<ProcessDimensionRelEntity> page = new Page<>(entity.getCurrentPage(), entity.getPageSize());

        //分页查询
        Page<ProcessDimensionRelEntity> result = processDimensionRelService.selPageList(page, entity);

        //拼装返回参数
        CommonResponse resp = new CommonResponse();
        resp.setSuccess();
        resp.setData(result);

        return resp;
    }


    /**
     * 分页查询 数据资源对象，根据参数roleId(角色编码)，去查询出所有的资源，
     * 并且如果该数据资源和角色已经关联isAssociated="1"
     * 没有关联isAssociated="0"
     * @param entity
     * @return
     */
    @ApiOperation(value = "分页查询 数据资源对象，根据参数roleId(角色编码)，去查询出所有的资源"
            , notes = "分页查询 数据资源对象，根据参数roleId(角色编码)，去查询出所有的资源", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/selectRoleIdPage", produces = "application/json;charset=UTF-8")
    public CommonResponse selectRoleIdPage(@RequestBody ProcessDimensionRelDto entity) {
        if(entity.getCurrentPage() == null || entity.getPageSize() == null){
            entity.setCurrentPage(1);
            entity.setPageSize(10);
        }

        if(entity.getRoleId()== null){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"参数角色编码为空!");

        }

        Page<ProcessDimensionRelDto> page = new Page<>(entity.getCurrentPage(), entity.getPageSize());

        //分页查询
        Page<ProcessDimensionRelDto> result = processDimensionRelService.selectRoleIdPage(page,entity);

        //拼装返回参数
        CommonResponse resp = new CommonResponse();
        resp.setSuccess();
        resp.setData(result);

        return resp;
    }


    @ApiOperation(value = "不分页查询操作与元素关系", notes = "不分页查询操作与元素关系象", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/selectList", produces = "application/json;charset=UTF-8")
    public CommonResponse selectList(@RequestBody ProcessDimensionRelEntity entity) {
        List<ProcessDimensionRelEntity> list = processDimensionRelService.selectList(entity);

        CommonResponse resp = new CommonResponse();
        resp.setSuccess();
        resp.setData(list);

        return resp;
    }

    

    @ApiOperation(value = "查询维度对象单条", notes = "查询维度对象单条", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/selectOne", produces = "application/json;charset=UTF-8")
    public CommonResponse selectOne(@RequestBody ProcessDimensionRelEntity entity) {
        ProcessDimensionRelEntity ProcessDimensionRelEntity = processDimensionRelService.getProcessDimensionRelEntity(entity);

        if (ProcessDimensionRelEntity == null) {
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG, "数据不存在");

        }

        CommonResponse resp = new CommonResponse();
        resp.setSuccess();
        resp.setData(ProcessDimensionRelEntity);

        return resp;

    }

    @ApiOperation(value = "批量新增", notes = "批量新增", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/addList", produces = "application/json;charset=UTF-8")
    public CommonResponse addList(@RequestBody @Valid ListWrapper<ProcessDimensionRelEntity> list, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {
            log.error("【新增参数不正确】,list={}", list);
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG, bindingResult.getFieldError().getDefaultMessage());
        }

        //需要判断传递的参数中有重复的(业务动作编码+维度编码+许可值)，这种情况比较少见
        List<ProcessDimensionRelEntity> copyList =  list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                new TreeSet<>(Comparator.comparing(o -> o.getBpdCode() + "#" + o.getDudCode() + "#" + o.getPermitValue()))), ArrayList::new));

        if(list.size() != copyList.size()){
            log.error("【新增参数数据重复】,list={}", list);
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG, "新增参数数据本身重复");

        }

        //去查询出所有有效的业务
        BizscopeProcessDefineEntity bizscopeProcessDefineEntity = new BizscopeProcessDefineEntity();
        bizscopeProcessDefineEntity.setStatus(StatusEnums.FINISHED.getCode());
        List<BizscopeProcessDefineEntity> bpdList = bizscopeProcessDefineService.selectList(bizscopeProcessDefineEntity);
        Map<String,String> bpdMap = bpdList.stream().collect(Collectors.toMap(BizscopeProcessDefineEntity::getBpdCode,BizscopeProcessDefineEntity::getBpdName));


        //去查询出所有有效的维度
        DimensionUnitDefineEntity dimensionUnitDefineEntity = new DimensionUnitDefineEntity();
        dimensionUnitDefineEntity.setStatus(StatusEnums.FINISHED.getCode());
        List<DimensionUnitDefineEntity> dudList = dimensionUnitDefineService.selectList(dimensionUnitDefineEntity);
        Map<String,String> dudMap = dudList.stream().collect(Collectors.toMap(DimensionUnitDefineEntity::getDudCode,DimensionUnitDefineEntity::getDudName));


        if (list.size() == 0) {
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG, "数据为空，新增失败！");

        }else{
            List<ProcessDimensionRelEntity> listEntity = new ArrayList<>();
            for(ProcessDimensionRelEntity entity:list){

                //这个时候，应该去判断一下，这么写对性能有些影响，如果list的数量超过1000，再去优化
                //业务动作编码+维度编码+许可值不能重复
                ProcessDimensionRelEntity d1 = new ProcessDimensionRelEntity();
                d1.setBpdCode(entity.getBpdCode());//业务动作编码
                d1.setDudCode(entity.getDudCode());//维度编码
                d1.setPermitValue(entity.getPermitValue());//许可值
                d1.setPermitType(StatusEnums.FINISHED.getCode());//许可类型为允许的

                d1 = processDimensionRelService.getProcessDimensionRelEntity(d1);

                if(d1 != null ){
                    log.error("【新增参数许可值重复】,entity={}",entity);
                    return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"参数许可值重复");

                }else if( StringUtils.isEmpty(bpdMap.get(entity.getBpdCode()))){
                    //判断业务动作编码的合法性
                    log.error("【新增参数业务动作编码查询不到对应数据】,bpdCode={}",entity.getBpdCode());
                    return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"参数业务动作编码不存在！");

                }else if( StringUtils.isEmpty(dudMap.get(entity.getDudCode())) ){
                    //判断维度编码的合法性
                    log.error("【新增参数维度编码查询不到对应数据】,bpdCode={}",entity.getBpdCode());
                    return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"参数维度编码不存在！");

                }

                entity.setCreateTime(new Date());//新增时间
                entity.setLastmodifiTime(new Date());//修改时间

                if(null == entity.getTenantId()){
                    entity.setTenantId(TenantIdEnums.BBG.getCode());
                }

                if(StringUtils.isEmpty(entity.getPdrCode())){//关系编码=控制点编码+维度编码+许可类型+许可值
                    entity.setPdrCode(entity.getBpdCode()+"_"+entity.getDudCode()+"_"+entity.getPermitType()+"_"+entity.getPermitValue());
                }

                if(StringUtils.isEmpty(entity.getPdrName())){//关系名称
                    entity.setPdrName(bpdMap.get(entity.getBpdCode())+"_"+dudMap.get(entity.getDudCode())+"_"+entity.getPermitType()+"_"+entity.getPermitValue());
                }

                if(StringUtils.isEmpty(entity.getPdrDesc())){//关系描述
                    entity.setPdrDesc(bpdMap.get(entity.getBpdCode())+"_"+dudMap.get(entity.getDudCode())+"_"+entity.getPermitType()+"_"+entity.getPermitValue());
                }

                listEntity.add(entity);
            }

            int isAdded = processDimensionRelService.addList(listEntity);

            if(isAdded>0){
                return  CommonResponse.success(listEntity);
            }else{
                return  CommonResponse.fail(CodeConstants.FAIL_CODE, "新增失败!");
            }
        }

    }


    @ApiOperation(value = "修改", notes = "修改不可以改pdrId，bpdCode，dudCode", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/update", produces = "application/json;charset=UTF-8")
    public CommonResponse update(@RequestBody @Valid ProcessDimensionRelEntity entity, BindingResult bindingResult) {

        //参数校验
        if(bindingResult.hasErrors()){
            log.error("【修改参数不正确】,entity={}",entity);
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,bindingResult.getFieldError().getDefaultMessage());
        }

        //根据pdrId 查询数据存在
        ProcessDimensionRelEntity d1  = new ProcessDimensionRelEntity();
        d1.setPdrId(entity.getPdrId());
        d1 = processDimensionRelService.getProcessDimensionRelEntity(d1);

        if(d1 == null){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"数据不存在");

        }

        //校验数据唯一  业务动作编码+维度编码下的许可值不能重复
        ProcessDimensionRelEntity d2 = new ProcessDimensionRelEntity();
        d2.setBpdCode(entity.getBpdCode());//业务动作编码
        d2.setDudCode(entity.getDudCode());//维度编码
        d2.setPermitValue(entity.getPermitValue());//许可值
        d2.setPermitType(entity.getPermitType());//许可类型

        d2 = processDimensionRelService.getProcessDimensionRelEntity(d2);

        if(d2 != null && !d2.getPdrId().equals(entity.getPdrId())){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"许可值重复");
        }


        entity.setLastmodifiTime(new Date());//修改时间

        int isAdded = processDimensionRelService.update(entity);

        if(isAdded>0){
            return  CommonResponse.success(entity);
        }else{
            return  CommonResponse.fail(CodeConstants.FAIL_CODE, "修改失败!");
        }

    }



    @ApiOperation(value = "根据控制点+维度批量更新操作", notes = "根据控制点+维度批量更新", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/updateBatch", produces = "application/json;charset=UTF-8")
    public CommonResponse updateBatch(@RequestBody @Valid ListWrapper<ProcessDimensionRelEntity> list, BindingResult bindingResult) {

        //参数校验
        if(bindingResult.hasErrors()){
            log.error("【修改参数不正确】,entity={}",list);
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,bindingResult.getFieldError().getDefaultMessage());
        }


        if(list.size() == 0){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG, "数据为空，更新失败！");

        }

        //需要判断传递的参数中有重复的(业务动作编码+维度编码+许可值)，这种情况比较少见
        List<ProcessDimensionRelEntity> copyList =  list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                new TreeSet<>(Comparator.comparing(o -> o.getBpdCode() + "#" + o.getDudCode() + "#" + o.getPermitValue()))), ArrayList::new));

        if(list.size() != copyList.size()){
            log.error("【批量更新参数数据重复】,list={}", list);
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG, "批量更新参数数据本身重复");

        }



        //去查询出所有有效的业务
        BizscopeProcessDefineEntity bizscopeProcessDefineEntity = new BizscopeProcessDefineEntity();
        bizscopeProcessDefineEntity.setStatus(StatusEnums.FINISHED.getCode());
        List<BizscopeProcessDefineEntity> bpdList = bizscopeProcessDefineService.selectList(bizscopeProcessDefineEntity);
        Map<String,String> bpdMap = bpdList.stream().collect(Collectors.toMap(BizscopeProcessDefineEntity::getBpdCode,BizscopeProcessDefineEntity::getBpdName));


        //去查询出所有有效的维度
        DimensionUnitDefineEntity dimensionUnitDefineEntity = new DimensionUnitDefineEntity();
        dimensionUnitDefineEntity.setStatus(StatusEnums.FINISHED.getCode());
        List<DimensionUnitDefineEntity> dudList = dimensionUnitDefineService.selectList(dimensionUnitDefineEntity);
        Map<String,String> dudMap = dudList.stream().collect(Collectors.toMap(DimensionUnitDefineEntity::getDudCode,DimensionUnitDefineEntity::getDudName));


        List<ProcessDimensionRelEntity> listEntity = new ArrayList<>();
        for(ProcessDimensionRelEntity entity:list){
            if( StringUtils.isEmpty(bpdMap.get(entity.getBpdCode()))){
                //判断业务动作编码的合法性
                log.error("【更新参数业务动作编码查询不到对应数据】,bpdCode={}",entity.getBpdCode());
                return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"参数业务动作编码不存在！");

            }else if( StringUtils.isEmpty(dudMap.get(entity.getDudCode())) ){
                //判断维度编码的合法性
                log.error("【更新参数维度编码查询不到对应数据】,bpdCode={}",entity.getBpdCode());
                return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"参数维度编码不存在！");

            }

            entity.setCreateTime(new Date());//新增时间
            entity.setLastmodifiTime(new Date());//修改时间

            if(null == entity.getTenantId()){
                entity.setTenantId(TenantIdEnums.BBG.getCode());
            }

            if(StringUtils.isEmpty(entity.getPdrCode())){//关系编码=控制点编码+维度编码+许可类型+许可值
                entity.setPdrCode(entity.getBpdCode()+"_"+entity.getDudCode()+"_"+entity.getPermitType()+"_"+entity.getPermitValue());
            }

            if(StringUtils.isEmpty(entity.getPdrName())){//关系名称
                entity.setPdrName(bpdMap.get(entity.getBpdCode())+"_"+dudMap.get(entity.getDudCode())+"_"+entity.getPermitType()+"_"+entity.getPermitValue());
            }

            if(StringUtils.isEmpty(entity.getPdrDesc())){//关系描述
                entity.setPdrDesc(bpdMap.get(entity.getBpdCode())+"_"+dudMap.get(entity.getDudCode())+"_"+entity.getPermitType()+"_"+entity.getPermitValue());
            }

            listEntity.add(entity);
        }

        int isAdded = processDimensionRelService.updateBatch(listEntity);

        if(isAdded>0){
            return  CommonResponse.success(listEntity);
        }else{
            return  CommonResponse.fail(CodeConstants.FAIL_CODE, "更新失败!");
        }



    }


    @ApiOperation(value = "根据维度批量更新操作", notes = "根据维度批量更新", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/updateDudCodeBatch", produces = "application/json;charset=UTF-8")
    public CommonResponse updateDudCodeBatch(@RequestBody @Valid ListWrapper<ProcessDimensionRelEntity> list, BindingResult bindingResult) {

        //参数校验
        if(bindingResult.hasErrors()){
            log.error("【修改参数不正确】,entity={}",list);
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,bindingResult.getFieldError().getDefaultMessage());
        }


        if(list.size() == 0){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG, "数据为空，更新失败！");

        }

        //需要判断传递的参数中有重复的(维度编码+许可值)，这种情况比较少见
        List<ProcessDimensionRelEntity> copyList =  list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                new TreeSet<>(Comparator.comparing(o -> o.getDudCode() + "#" + o.getPermitValue()))), ArrayList::new));

        if(list.size() != copyList.size()){
            log.error("【批量更新参数数据重复】,list={}", list);
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG, "批量更新参数数据本身重复");

        }

        int isAdded = processDimensionRelService.updateDudCodeBatch(list);

        if(isAdded>0){
            return  CommonResponse.success(list);
        }else{
            return  CommonResponse.fail(CodeConstants.FAIL_CODE, "更新失败!");
        }

    }



    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    public CommonResponse delete(@RequestBody ProcessDimensionRelEntity entity) {
        //校验 数据存在
        ProcessDimensionRelEntity d1  = new ProcessDimensionRelEntity();
        d1.setPdrId(entity.getPdrId());
        d1 = processDimensionRelService.getProcessDimensionRelEntity(d1);

        if(d1 == null){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"数据不存在");

        }

        int isAdded = processDimensionRelService.delete(entity);

        if(isAdded>0){
            return  CommonResponse.success(entity);
        }else{
            return  CommonResponse.fail(CodeConstants.FAIL_CODE, "删除失败!");
        }


    }

    @ApiOperation(value = "批量删除", notes = "批量删除", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/deleteBatch", produces = "application/json;charset=UTF-8")
    public CommonResponse deleteBatch(@RequestBody Integer[] pdrIds) {
        //校验 数据存在
        if(pdrIds.length == 0 ){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"参数为空");

        }

        int isAdded = processDimensionRelService.deleteBatch(pdrIds);

        if(isAdded>0){
            return  CommonResponse.success(pdrIds);
        }else{
            return  CommonResponse.fail(CodeConstants.FAIL_CODE, "删除失败!");
        }


    }


    @ApiOperation(value = "根据bpdCode(控制点编码)删除", notes = "根据bpdCode(控制点编码)删除", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/deleteBpdCode", produces = "application/json;charset=UTF-8")
    public CommonResponse deleteBpdCode(@RequestBody ProcessDimensionRelEntity entity) {
        //校验 bpdCode  有值

        if(StringUtils.isEmpty(entity.getBpdCode())){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"控制点编码为空!");

        }

        List<ProcessDimensionRelEntity>  list = processDimensionRelService.selectList(entity);
        if(list.size() == 0){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"数据不存在，删除失败!");

        }

        int isAdded = processDimensionRelService.deleteBpdCode(entity);

        if(isAdded>0){
            return  CommonResponse.success(entity);
        }else{
            return  CommonResponse.fail(CodeConstants.FAIL_CODE, "删除失败!");
        }


    }

    @ApiOperation(value = "根据bpdCode(控制点编码)+dudCode(维度编码)删除", notes = "根据bpdCode(控制点编码)+dudCode(维度编码)删除", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/deleteBpdCodeAndDudCode", produces = "application/json;charset=UTF-8")
    public CommonResponse deleteBpdCodeAndDudCode(@RequestBody ProcessDimensionRelEntity entity) {
        //校验 bpdCode dudCode 有值

        if(StringUtils.isEmpty(entity.getBpdCode()) || StringUtils.isEmpty(entity.getDudCode())){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"参数为空!");

        }

        int isAdded = processDimensionRelService.deleteBpdCodeAndDudCode(entity);


        if(isAdded>0){
            return  CommonResponse.success(entity);
        }else{
            return  CommonResponse.fail(CodeConstants.FAIL_CODE, "删除失败!");
        }


    }


    @ApiOperation(value = "根据角色id集合+bpdCode(控制点编码)+dudCode(维度编码)查询出数据资源关联集合", notes = "不分页查询角色许可关系表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/selectRoleIds", produces = "application/json;charset=UTF-8")
    public CommonResponse selectRoleIds(@RequestBody RolepermissionRelReq entity) {
        List<ProcessDimensionRelEntity > list = processDimensionRelService.selectRoleIds(entity);

        CommonResponse resp = new CommonResponse();
        resp.setSuccess();
        resp.setData(list);

        return resp;
    }


    @ApiOperation(value = "根据角色id集合+bpdCode(控制点编码)+dudCode(维度编码)查询出数据资源关联集合", notes = "不分页查询角色许可关系表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/selectRoleIdsFeign", produces = "application/json;charset=UTF-8")
    public List<ProcessDimensionRelEntity > selectRoleIdsFeign(@RequestBody RolepermissionRelReq entity) {
        List<ProcessDimensionRelEntity > list = processDimensionRelService.selectRoleIds(entity);

        return list;
    }




}