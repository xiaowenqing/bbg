package com.bbg.bizdatapermissionmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Author glaive
 * Date  2020-03-05
 */
@Data
@TableName(value="dimension_unit_define")
@ApiModel(value = "元素（维度）定义(基础资源，被授权的分类信息)")
public class DimensionUnitDefineEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户ID")
    @Max(value = 11,message = "租户ID长度超过11位")
    private Integer tenantId;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "维度编码")
    @NotNull(message = "维度编码必填")
    @Size(max = 100,message = "维度编码长度超过100位")
    private String dudCode;

    @ApiModelProperty(value = "维度名称")
    @NotNull(message = "维度名称必填")
    @Size(max = 100,message = "维度名称长度超过100位")
    private String dudName;

    @ApiModelProperty(value = "维度描述")
    @Size(max = 100,message = "维度描述长度超过100位")
    private String dudDesc;

    @ApiModelProperty(value = "是否最终核查维度：0不是，1是")
    @NotNull(message = "最终核查维度必填")
    @Size(max = 1,message = "最终核查维度超过1位")
    private String dudIseffective;

    @ApiModelProperty(value = "维度数据来源定义URL（无参数接口，用于维护）")
    @NotNull(message = "维度数据来源定义URL必填")
    @Size(max = 100,message = "维度数据来源定义URL长度超过100位")
    private String dudSreurl;

    @ApiModelProperty(value = "当前维度取值key")
    @Size(max = 100,message = "当前维度取值key长度超过100位")
    private String dudObkey;

    @ApiModelProperty(value = "父维度取值key")
    @Size(max = 100,message = "父维度取值key长度超过100位")
    private String dudObpkey;

    @ApiModelProperty(value = "父维度（上级维度）")
    @Size(max = 100,message = "父维度长度超过100位")
    private String dudPcode;

    @ApiModelProperty(value = "记录有效性：1有效，0无效")
    @NotNull(message = "记录有效性必填")
    @Size(max = 1,message = "记录有效性长度超过1位")
    private String status;


    @ApiModelProperty(value = "创建人")
    private String creater;

    @ApiModelProperty(value = "创建日期 ")
    @JsonFormat(timezone="GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    private String lastmodifier;

    @ApiModelProperty(value = "修改日期 ")
    @JsonFormat(timezone="GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastmodifiTime;



}