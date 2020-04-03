package com.bbg.bizdatapermissionmanager.controller;    /**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author xwq
 * @date 2020/3/5 000518:18
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbg.bizdatapermissionmanager.common.dto.DimensionUnitDefineDto;
import com.bbg.bizdatapermissionmanager.common.enums.TenantIdEnums;
import com.bbg.bizdatapermissionmanager.entity.DimensionUnitDefineEntity;
import com.bbg.bizdatapermissionmanager.entity.ProcessDimensionRelEntity;
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
import java.util.Date;
import java.util.List;

/**
 * 维度控制类
 * @author xwq
 * @create 2020-03-05 18:18
 **/
@RestController
@Api(tags = "维度控制类")
@RequestMapping(value = "/dimensionUnitDefine")
@Slf4j
public class DimensionUnitDefineController {

    @Autowired
    private DimensionUnitDefineService dimensionUnitDefineService;

    @Autowired
    private ProcessDimensionRelService processDimensionRelService;

    @ApiOperation(value = "分页查询维度对象", notes = "分页查询维度对象", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/page", produces = "application/json;charset=UTF-8")
    public CommonResponse page(@RequestBody DimensionUnitDefineDto entity) {

        if(entity.getCurrentPage() == null || entity.getPageSize() == null){
            entity.setCurrentPage(1);
            entity.setPageSize(10);
        }

        Page<DimensionUnitDefineEntity> page = new Page<>(entity.getCurrentPage(), entity.getPageSize());

        //分页查询
        Page<DimensionUnitDefineEntity> result = dimensionUnitDefineService.selPageList(page,entity);

        //拼装返回参数
        CommonResponse resp = new CommonResponse();
        resp.setSuccess();
        resp.setData(result);

        return resp;
    }

    /**
     *  分页查询 根据控制点编码分页查询维度,已经关联isAssociated=1
     * 没有关联isAssociated="0"
     * @param entity
     * @return
     */
    @ApiOperation(value = "根据控制点编码分页查询维度,已经关联isAssociated=1",
            notes = "根据控制点编码分页查询维度,已经关联isAssociated=1", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/selectBpdCodePageList", produces = "application/json;charset=UTF-8")
    public CommonResponse selectBpdCodePageList(@RequestBody DimensionUnitDefineDto entity) {

        if(entity.getCurrentPage() == null || entity.getPageSize() == null){
            entity.setCurrentPage(1);
            entity.setPageSize(10);
        }

        Page<DimensionUnitDefineDto> page = new Page<>(entity.getCurrentPage(), entity.getPageSize());

        //分页查询
        Page<DimensionUnitDefineDto> result = dimensionUnitDefineService.selectBpdCodePageList(page,entity);

        //拼装返回参数
        CommonResponse resp = new CommonResponse();
        resp.setSuccess();
        resp.setData(result);

        return resp;
    }


    @ApiOperation(value = "根据控制点编码分页查询维度对象", notes = "根据控制点编码分页查询维度对象", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/selectBpdCodePage", produces = "application/json;charset=UTF-8")
    public CommonResponse selectBpdCodePage(@RequestBody DimensionUnitDefineDto entity) {
        if(entity.getCurrentPage() == null || entity.getPageSize() == null){
            entity.setCurrentPage(1);
            entity.setPageSize(10);
        }

        if(StringUtils.isEmpty(entity.getBpdCode())){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"参数控制点编码为空!");

        }

        Page<DimensionUnitDefineEntity> page = new Page<>(entity.getCurrentPage(), entity.getPageSize());

        //分页查询
        Page<DimensionUnitDefineEntity> result = dimensionUnitDefineService.selectBpdCodePage(page,entity);

        //拼装返回参数
        CommonResponse resp = new CommonResponse();
        resp.setSuccess();
        resp.setData(result);

        return resp;
    }


    @ApiOperation(value = "不分页查询维度对象", notes = "不分页查询维度对象", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/selectList", produces = "application/json;charset=UTF-8")
    public CommonResponse selectList(@RequestBody DimensionUnitDefineEntity entity) {
        List<DimensionUnitDefineEntity> list = dimensionUnitDefineService.selectList(entity);

        CommonResponse resp = new CommonResponse();
        resp.setSuccess();
        resp.setData(list);

        return resp;
    }

    @ApiOperation(value = "查询维度对象单条", notes = "查询维度对象单条", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/selectOne", produces = "application/json;charset=UTF-8")
    public CommonResponse selectOne(@RequestBody DimensionUnitDefineEntity entity) {
        DimensionUnitDefineEntity dimensionUnitDefineEntity = dimensionUnitDefineService.getDimensionUnitDefineEntity(entity);

        if(dimensionUnitDefineEntity == null){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"数据不存在");

        }

        CommonResponse resp = new CommonResponse();
        resp.setSuccess();
        resp.setData(dimensionUnitDefineEntity);

        return resp;

    }

    @ApiOperation(value = "新增维度", notes = "新增维度", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/add", produces = "application/json;charset=UTF-8")
    public CommonResponse add(@RequestBody @Valid DimensionUnitDefineEntity entity, BindingResult bindingResult) {

        //参数校验
        if(bindingResult.hasErrors()){
            log.error("【创建维度参数不正确】,entity={}",entity);
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,bindingResult.getFieldError().getDefaultMessage());
        }

        //校验dudCode（维度编码）唯一
        DimensionUnitDefineEntity d1  = new DimensionUnitDefineEntity();
        d1.setDudCode(entity.getDudCode());
        d1 = dimensionUnitDefineService.getDimensionUnitDefineEntity(d1);

        if(d1 != null){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"维度编码重复");

        }

        //校验dudName维度名称唯一
        DimensionUnitDefineEntity d2 = new DimensionUnitDefineEntity();
        d2.setDudName(entity.getDudName());
        d2 = dimensionUnitDefineService.getDimensionUnitDefineEntity(d2);

        if(d2 != null){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"维度名称重复");
        }

        //这里需要去控制父子关联只有俩级
        if(!StringUtils.isEmpty(entity.getDudPcode())){
            DimensionUnitDefineEntity dud = new DimensionUnitDefineEntity();
            dud.setDudCode(entity.getDudPcode());
            dud = dimensionUnitDefineService.getDimensionUnitDefineEntity(dud);

            if(dud == null){
                return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"父维度不存在");
            }else{//查询出父维度的数据之后，需要判断该数据的dudPcode（父维度）为空，保证就俩级
                if(!StringUtils.isEmpty(dud.getDudPcode())){
                    return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"父维度超过2层");

                }
            }
        }


        entity.setCreateTime(new Date());//新增时间
        entity.setLastmodifiTime(new Date());//修改时间


        if(null == entity.getTenantId()){
            entity.setTenantId(TenantIdEnums.BBG.getCode());
        }

        int isAdded = dimensionUnitDefineService.add(entity);

        if(isAdded>0){
            return  CommonResponse.success(entity);
        }else{
            return  CommonResponse.fail(CodeConstants.FAIL_CODE, "新增失败!");
        }
    }


    @ApiOperation(value = "修改维度", notes = "修改维度", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/update", produces = "application/json;charset=UTF-8")
    public CommonResponse update(@RequestBody @Valid DimensionUnitDefineEntity entity, BindingResult bindingResult) {

        //参数校验
        if(bindingResult.hasErrors()){
            log.error("【修改维度参数不正确】,entity={}",entity);
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,bindingResult.getFieldError().getDefaultMessage());
        }

        //校验dudCode（维度编码）数据存在
        DimensionUnitDefineEntity d1  = new DimensionUnitDefineEntity();
        d1.setDudCode(entity.getDudCode());
        d1 = dimensionUnitDefineService.getDimensionUnitDefineEntity(d1);

        if(d1 == null){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"维度编码不存在");

        }

        //校验dudName维度名称唯一
        DimensionUnitDefineEntity d2 = new DimensionUnitDefineEntity();
        d2.setDudName(entity.getDudName());
        d2 = dimensionUnitDefineService.getDimensionUnitDefineEntity(d2);

        if(d2 != null && !d2.getDudCode().equals(entity.getDudCode())){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"维度名称重复");
        }

        //这里需要去控制父子关联只有俩级
        if(!StringUtils.isEmpty(entity.getDudPcode())){
            DimensionUnitDefineEntity d3 = new DimensionUnitDefineEntity();
            d3.setDudCode(entity.getDudPcode());
            d3 = dimensionUnitDefineService.getDimensionUnitDefineEntity(d3);

            if(d3 == null){
                return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"父维度不存在");
            }else{//查询出父维度的数据之后，需要判断该数据的dudPcode（父维度）为空，保证就俩级
                if(!StringUtils.isEmpty(d3.getDudPcode())){
                    return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"父维度超过2层");

                }
            }
        }

        entity.setLastmodifiTime(new Date());//修改时间

        int isAdded = dimensionUnitDefineService.update(entity);

        if(isAdded>0){
            return  CommonResponse.success(entity);
        }else{
            return  CommonResponse.fail(CodeConstants.FAIL_CODE, "修改失败!");
        }

    }

    @ApiOperation(value = "根据dudCode（维度编码）删除维度", notes = "删除维度", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    public CommonResponse delete(@RequestBody DimensionUnitDefineEntity entity) {
        //校验dudCode（维度编码）数据存在
        DimensionUnitDefineEntity d1  = new DimensionUnitDefineEntity();
        d1.setDudCode(entity.getDudCode());
        d1 = dimensionUnitDefineService.getDimensionUnitDefineEntity(d1);

        if(d1 == null){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"维度编码不存在");

        }

        //判断维度是否在资源中有引用
        ProcessDimensionRelEntity pdrEntity = new ProcessDimensionRelEntity();
        pdrEntity.setDudCode(entity.getDudCode());
        List<ProcessDimensionRelEntity> list = processDimensionRelService.selectList(pdrEntity);

        if(list.size()>0){
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"维度有引用，请先删除引用！");

        }


        int isAdded = dimensionUnitDefineService.delete(entity);

        if(isAdded>0){
            return  CommonResponse.success(entity);
        }else{
            return  CommonResponse.fail(CodeConstants.FAIL_CODE, "删除失败!");
        }


    }

}
