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
@TableName(value="process_dimension_rel")
@ApiModel(value = "操作与元素关系表(被授权资源-控制点+基础资源值)")
public class ProcessDimensionRelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户ID")
    @Max(value = 11,message = "租户ID长度超过11位")
    private Integer tenantId;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "操作与元素关系流水PK")
    private Integer pdrId;

    @ApiModelProperty(value = "被许可资源编码(关系编码)")
    @Size(max = 100,message = "被许可资源编码长度超过100位")
    private String pdrCode;

    @ApiModelProperty(value = "被许可资源名称(关系名称)")
    @Size(max = 100,message = "被许可资源名称长度超过100位")
    private String pdrName;

    @ApiModelProperty(value = "被许可资源的描述（关系描述）")
    @Size(max = 100,message = "被许可资源的描述长度超过100位")
    private String pdrDesc;

    @ApiModelProperty(value = "控制点编码")
    @NotNull(message = "控制点编码必填")
    @Size(max = 100,message = "控制点编码长度超过100位")
    private String bpdCode;

    private String bpdName;

    private String bpdStatus;

    @ApiModelProperty(value = "维度编码")
    @NotNull(message = "维度编码必填")
    @Size(max = 100,message = "维度编码长度超过100位")
    private String dudCode;

    private String dudName;

    private String dudStatus;

    @ApiModelProperty(value = "一个被许可的值可能存在一个父级的维度的值作为分组（许可父值）")
    @Size(max = 100,message = "许可父值长度超过100位")
    private String permitPvalue;

    @ApiModelProperty(value = "许可对象的值（许可值）")
    @Size(max = 200,message = "许可值长度超过200位")
    private String permitValue;

    @ApiModelProperty(value = "许可，拒绝(肯定、拒绝)许可类型：0拒绝，1肯定")
    @NotNull(message = "许可类型必填")
    @Size(max = 1,message = "许可类型长度超过1位")
    private String permitType;

    @ApiModelProperty(value = "有效性：0无效，1有效")
    @NotNull(message = "有效性必填")
    @Size(max = 1,message = "有效性长度超过1位")
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