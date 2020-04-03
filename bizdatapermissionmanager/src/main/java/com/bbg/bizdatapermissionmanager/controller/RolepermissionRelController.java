package com.bbg.bizdatapermissionmanager.controller;    /**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author xwq
 * @date 2020/3/7 000715:46
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbg.bizdatapermissionmanager.common.dto.ListWrapper;
import com.bbg.bizdatapermissionmanager.common.dto.RolepermissionRelDto;
import com.bbg.bizdatapermissionmanager.common.enums.StatusEnums;
import com.bbg.bizdatapermissionmanager.common.enums.TenantIdEnums;
import com.bbg.bizdatapermissionmanager.entity.*;
import com.bbg.bizdatapermissionmanager.entity.RolepermissionRelEntity;
import com.bbg.bizdatapermissionmanager.service.ProcessDimensionRelService;
import com.bbg.bizdatapermissionmanager.service.RolepermissionRelService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author xwq
 * @create 2020-03-07 15:46
 **/
@RestController
@Api(tags = "角色许可关系表控制类")
@RequestMapping(value = "/rolepermissionRel")
@Slf4j
public class RolepermissionRelController {
    @Autowired
    private RolepermissionRelService rolepermissionRelService;

    @Autowired
    private ProcessDimensionRelService processDimensionRelService;

    @ApiOperation(value = "分页查询角色许可关系表", notes = "分页查询角色许可关系表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/page", produces = "application/json;charset=UTF-8")
    public CommonResponse page(@RequestBody RolepermissionRelDto entity) {

        if (entity.getCurrentPage() == null || entity.getPageSize() == null) {
            entity.setCurrentPage(1);
            entity.setPageSize(10);
        }

        Page<RolepermissionRelEntity> page = new Page<>(entity.getCurrentPage(), entity.getPageSize());

        //分页查询
        Page<RolepermissionRelEntity> result = rolepermissionRelService.selPageList(page, entity);

        //拼装返回参数
        CommonResponse resp = new CommonResponse();
        resp.setSuccess();
        resp.setData(result);

        return resp;
    }


    @ApiOperation(value = "不分页查询角色许可关系表", notes = "不分页查询角色许可关系表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/selectList", produces = "application/json;charset=UTF-8")
    public CommonResponse selectList(@RequestBody RolepermissionRelEntity entity) {
        List<RolepermissionRelEntity> list = rolepermissionRelService.selectList(entity);

        CommonResponse resp = new CommonResponse();
        resp.setSuccess();
        resp.setData(list);

        return resp;
    }



    @ApiOperation(value = "查询角色许可关系单条", notes = "查询角色许可关系单条", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/selectOne", produces = "application/json;charset=UTF-8")
    public CommonResponse selectOne(@RequestBody RolepermissionRelEntity entity) {
        RolepermissionRelEntity RolepermissionRelEntity = rolepermissionRelService.getRolepermissionRelEntity(entity);

        if (RolepermissionRelEntity == null) {
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG, "数据不存在");

        }

        CommonResponse resp = new CommonResponse();
        resp.setSuccess();
        resp.setData(RolepermissionRelEntity);

        return resp;

    }

    @ApiOperation(value = "批量新增", notes = "批量新增", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/addList", produces = "application/json;charset=UTF-8")
    public CommonResponse addList(@RequestBody @Valid ListWrapper<RolepermissionRelEntity> list, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {
            log.error("【新增参数不正确】,list={}", list);
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG, bindingResult.getFieldError().getDefaultMessage());
        }

        if (list.size() == 0) {
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG, "数据为空，新增失败！");

        }


        //需要去校验许可资源编码有效
        ProcessDimensionRelEntity pdrEntity = new ProcessDimensionRelEntity();
        pdrEntity.setStatus(StatusEnums.FINISHED.getCode());
        List<ProcessDimensionRelEntity> pdrList = processDimensionRelService.selectList(pdrEntity);
        Map<Integer,String> pdrMap = pdrList.stream().collect(Collectors.toMap(ProcessDimensionRelEntity::getPdrId,ProcessDimensionRelEntity::getDudCode));

        List<RolepermissionRelEntity> listEntity = new ArrayList<>();

        for(RolepermissionRelEntity entity : list){
            //判断pdrId有效
            if(StringUtils.isEmpty(pdrMap.get(entity.getPdrId()))){
                log.error("【新增许可资源编码查询不到对应数据】,bpdCode={}",entity.getPdrId());
                return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"参数许可资源编码不存在");

            }else{//判断不能新增重复数据
                RolepermissionRelEntity r1 = new RolepermissionRelEntity();
                r1.setRoleId(entity.getRoleId());
                r1.setPdrId(entity.getPdrId());
                r1 = rolepermissionRelService.getRolepermissionRelEntity(r1);

                if(r1 != null){
                    log.error("【新增参数重复】,entity={}",entity);
                    return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"参数重复");

                }
            }

            entity.setCreateTime(new Date());//新增时间
            entity.setLastmodifiTime(new Date());//修改时间

            if(null == entity.getTenantId()){
                entity.setTenantId(TenantIdEnums.BBG.getCode());
            }

            listEntity.add(entity);

        }

        int isAdded = rolepermissionRelService.addList(listEntity);

        if(isAdded>0){
            return  CommonResponse.success(listEntity);
        }else{
            return  CommonResponse.fail(CodeConstants.FAIL_CODE, "新增失败!");
        }
    }

    @ApiOperation(value = "修改", notes = "修改好像就可以改状态了", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/update", produces = "application/json;charset=UTF-8")
    public CommonResponse update(@RequestBody @Valid RolepermissionRelEntity entity, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {
            log.error("【修改参数不正确】,entity={}", entity);
            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG, bindingResult.getFieldError().getDefaultMessage());
        }

        //校验数据是否存在
        RolepermissionRelEntity r1 = new RolepermissionRelEntity();
        
        r1.setRprId(entity.getRprId());
        r1= rolepermissionRelService.getRolepermissionRelEntity(r1);
        if(r1 == null){
            log.error("【数据不存在】,entity={}", entity);

            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"数据不存在");

        }

        entity.setLastmodifiTime(new Date());//修改时间
        int isAdded = rolepermissionRelService.update(entity);

        if(isAdded>0){
            return  CommonResponse.success(entity);
        }else{
            return  CommonResponse.fail(CodeConstants.FAIL_CODE, "修改失败!");
        }


    }


    @ApiOperation(value = "删除", notes = "根据主键删除", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    public CommonResponse delete(@RequestBody @Valid RolepermissionRelEntity entity, BindingResult bindingResult) {
        //校验数据是否存在
        RolepermissionRelEntity r1 = new RolepermissionRelEntity();

        r1.setRprId(entity.getRprId());
        r1= rolepermissionRelService.getRolepermissionRelEntity(entity);
        if(r1 == null){
            log.error("【数据不存在】,entity={}", entity);

            return CommonResponse.fail(CodeConstants.PARAM_ERROR_MSG,"数据不存在");

        }

        int isAdded = rolepermissionRelService.delete(entity);

        if(isAdded>0){
            return  CommonResponse.success(entity);
        }else{
            return  CommonResponse.fail(CodeConstants.FAIL_CODE, "删除失败!");
        }
    }



}
