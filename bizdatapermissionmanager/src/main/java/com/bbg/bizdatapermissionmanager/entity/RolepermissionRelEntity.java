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
@TableName(value="rolepermission_rel")
@ApiModel(value = "角色许可关系表")
public class RolepermissionRelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户ID")
    @Max(value = 11,message = "租户ID长度超过11位")
    private Integer tenantId;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "许可流水ID")
    private Integer rprId;

    @ApiModelProperty(value = "角色编码(ID)")
    @NotNull(message = "角色编码必填")
    @Max(value = 11,message = "角色编码长度超过11位")
    private Integer roleId;

    @ApiModelProperty(value = "被授权的资源（许可资源编码）")
    @NotNull(message = "许可资源编码必填")
    @Max(value = 11,message = "许可资源编码长度超过11位")
    private Integer pdrId;

    @ApiModelProperty(value = "记录状态：0无效，1有效")
    @NotNull(message = "记录状态必填")
    @Size(max = 1,message = "记录状态长度超过1位")
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

    private String bpdCode;//控制点编码

    private String bpdName;//控制点名

    private String bpdDesc;//控制点描述

    private String bpdSrvcode;//控制点所在服务

    private String bpdSrvdesc;//业务所在服务描述

    private String bpdStatus;//控制点状态






    private String dudCode;//维度编码

    private String dudName;//维度名称

    private String dudDesc;//维度描述

    private String dudIseffective;//是否最终核查维度：0不是，1是

    private String dudSreurl;//维度数据来源定义URL

    private String dudObkey;//当前维度取值key

    private String dudObpkey;//父维度取值key

    private String dudPcode;//父维度

    private String dudStatus;//记录有效性：1有效，0无效



    private String pdrCode;//被许可资源编码

    private String pdrName;//被许可资源名称

    private String pdrDesc;//被许可资源的描述

    private String permitPvalue;//许可父值

    private String permitValue;//许可值

    private String permitType;//许可，拒绝(肯定、拒绝)许可类型：0拒绝，1肯定

    private String pdrStatus;//有效性：0无效，1有效



}