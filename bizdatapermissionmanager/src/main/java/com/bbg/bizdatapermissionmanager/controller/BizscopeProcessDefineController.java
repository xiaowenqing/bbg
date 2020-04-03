package com.bbg.bizdatapermissionmanager.controller;    /**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author xwq
 * @date 2020/3/5 00052:03
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbg.bizdatapermissionmanager.common.dto.BizscopeProcessDefineDto;
import com.bbg.bizdatapermissionmanager.common.enums.TenantIdEnums;
import com.bbg.bizdatapermissionmanager.entity.BizscopeProcessDefineEntity;
import com.bbg.bizdatapermissionmanager.entity.ProcessDimensionRelEntity;
import com.bbg.bizdatapermissionmanager.service.BizscopeProcessDefineService;
import com.bbg.bizdatapermissionmanager.service.ProcessDimensionRelService;
import com.cloud.domain.response.CodeConstants;
import com.cloud.domain.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;


/**
 *
 * @author xwq
 * @create 2020-03-05 2:03
 **/
@RestController
@Api(tags = "业务模块控制类")
@RequestMapping(value = "/bizscopeProcessDefine")
@Slf4j
public class BizscopeProcessDefineController {

    @Autowired
    private BizscopeProcessDefineService bizscopeProcessDefineService;

    @Autowired
    private ProcessDimensionRelService processDimensionRelService;



    @ApiOperation(value = "分页查询业务模块对象", notes = "分页查询业务模块对象", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/page", produces = "application/json;charset=UTF-8")
    public CommonResponse page(@RequestBody BizscopeProcessDefineDto entity) {

        if(entity.getCurrentPage() == null || entity.getPageSize() == null){
            entity.setCurrentPage(1);
            entity.setPageSize(10);
        }

        Page<BizscopeProcessDefineEntity> page = new Page<>(entity.getCurrentPage(), entity.getPageSize());

        //分页查询
        Page<BizscopeProcessDefineEntity> result = bizscopeProcessDefineService.selPageList(page,entity);

        //拼装返回参数
        CommonResponse resp = new CommonResponse();
        resp.setSuccess();
        resp.setData(result);

        return resp;
    }

    @ApiOperation(value = "查询业务模块对象不分页", notes = "查询业务模块对象不分页", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/selectList", produces = "application/json;charset=UTF-8")
    public CommonResponse selectList(@RequestBody BizscopeProcessDefineEntity entity) {
       List<BizscopeProcessDefineEntity> list= bizscopeProcessDefineService.selectList(entity);

        CommonResponse resp = new CommonResponse();
        resp.setSuccess();
        resp.setData(list);

        return resp;
    }

    @ApiOperation(value = "查询业务模块单条", notes = "查询业务模块对象只会返回单条", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/selectOne", produces = "application/json;charset=UTF-8")
    public CommonResponse selectOne(@RequestBody BizscopeProcessDefineEntity entity) {
        BizscopeProcessDefineEntity bizscopeProcessDefineEntity = bizscopeProcessDefineService.getBizscopeProcessDefineEntity(entity);

        if(bizscopeProcessDefineEntity ==null){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"数据不存在");
        }

        CommonResponse resp = new CommonResponse();
        resp.setSuccess();
        resp.setData(bizscopeProcessDefineEntity);

        return resp;
    }

    @ApiOperation(value = "新增业务模块", notes = "新增业务模块", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/add", produces = "application/json;charset=UTF-8")
    public CommonResponse add( @RequestBody @Valid BizscopeProcessDefineEntity entity, BindingResult bindingResult) {

        //参数校验
        if(bindingResult.hasErrors()){
            log.error("【创建业务模块参数不正确】,entity={}",entity);
           return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,bindingResult.getFieldError().getDefaultMessage());
        }

        //校验bpdCode(控制点编码)唯一
        BizscopeProcessDefineEntity b1 = new BizscopeProcessDefineEntity();
        b1.setBpdCode(entity.getBpdCode());
        b1 = bizscopeProcessDefineService.getBizscopeProcessDefineEntity(b1);
        if(b1 != null){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"控制点编码重复");
        }

        //校验bpdName（控制点名）唯一
        BizscopeProcessDefineEntity b2 = new BizscopeProcessDefineEntity();
        b2.setBpdName(entity.getBpdName());
        b2 = bizscopeProcessDefineService.getBizscopeProcessDefineEntity(b2);
        if(b2 != null){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"控制点名重复");
        }

        entity.setCreateTime(new Date());//新增时间
        entity.setLastmodifiTime(new Date());//修改时间

        if(null == entity.getTenantId()){
            entity.setTenantId(TenantIdEnums.BBG.getCode());
        }

        int isAdded = bizscopeProcessDefineService.add(entity);

        if(isAdded>0){
           return  CommonResponse.success(entity);
        }else{
           return  CommonResponse.fail(CodeConstants.FAIL_CODE, "新增失败!");
        }

    }


    @ApiOperation(value = "修改业务模块", notes = "修改业务模块", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/update", produces = "application/json;charset=UTF-8")
    public CommonResponse update( @RequestBody @Valid BizscopeProcessDefineEntity entity, BindingResult bindingResult) {
        //参数校验
        if(bindingResult.hasErrors()){
            log.error("【修改业务模块参数不正确】,entity={}",entity);
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,bindingResult.getFieldError().getDefaultMessage());
        }

        //校验bpdCode(控制点编码)确定数据存在
        BizscopeProcessDefineEntity b1 = new BizscopeProcessDefineEntity();
        b1.setBpdCode(entity.getBpdCode());
        b1 = bizscopeProcessDefineService.getBizscopeProcessDefineEntity(b1);
        if(b1 == null ){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"控制点编码不存在");
        }

        //校验bpdName（控制点名）唯一
        BizscopeProcessDefineEntity b2 = new BizscopeProcessDefineEntity();
        b2.setBpdName(entity.getBpdName());
        b2 = bizscopeProcessDefineService.getBizscopeProcessDefineEntity(b2);
        if(b2 != null && !b2.getBpdCode().equals(entity.getBpdCode())){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"控制点名重复");
        }

        entity.setLastmodifiTime(new Date());

        int isAdded = bizscopeProcessDefineService.update(entity);

        if(isAdded>0){
            return  CommonResponse.success(entity);
        }else{
            return  CommonResponse.fail(CodeConstants.FAIL_CODE, "修改失败!");
        }
    }


    @ApiOperation(value = "根据bpdCode(控制点编码)删除业务模块", notes = "删除业务模块", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    public CommonResponse delete( @RequestBody  BizscopeProcessDefineEntity entity) {

        //校验bpdCode(控制点编码)确定数据存在
        BizscopeProcessDefineEntity b1 = new BizscopeProcessDefineEntity();
        b1.setBpdCode(entity.getBpdCode());
        b1 = bizscopeProcessDefineService.getBizscopeProcessDefineEntity(b1);
        if(b1 == null ){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"控制点编码不存在");
        }

        //判断资源中是否有引用，如果有引用就不能删除
        ProcessDimensionRelEntity pdrEntity = new ProcessDimensionRelEntity();
        pdrEntity.setBpdCode(entity.getBpdCode());
        List<ProcessDimensionRelEntity> list = processDimensionRelService.selectList(pdrEntity);

        if(list.size()>0){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"控制点有引用，请先删除引用！");

        }


        int isAdded = bizscopeProcessDefineService.delete(entity);

        if(isAdded>0){
            return  CommonResponse.success(entity);
        }else{
            return  CommonResponse.fail(CodeConstants.FAIL_CODE, "删除失败!");
        }

    }

}
