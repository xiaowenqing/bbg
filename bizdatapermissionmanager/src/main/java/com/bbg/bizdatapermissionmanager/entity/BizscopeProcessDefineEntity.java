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
 *	
 *  表名:业务模块实体类
 *  注释:
 *  创建人: glaive
 *  创建日期:2020-02-07 21:24:28
 */
@Data
@TableName(value="bizscope_process_define")
@ApiModel(value = "业务模块实体类")
public class BizscopeProcessDefineEntity implements Serializable  {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "租户ID")
	@Max(value = 11,message = "租户ID长度超过11位")
	private Integer tenantId;

	@TableId(type = IdType.AUTO)
	@ApiModelProperty(value = "控制点编码")
	@NotNull(message = "控制点编码必填")
	@Size(max = 100,message = "控制点编码长度超过100位")
	private String bpdCode;

	@ApiModelProperty(value = "控制点名")
	@NotNull(message = "控制点名必填")
	@Size(max = 100,message = "控制点名长度超过100位")
	private String bpdName;

	@ApiModelProperty(value = "控制点描述")
	@Size(max = 100,message = "控制点描述长度超过100位")
	private String bpdDesc;

	@ApiModelProperty(value = "控制点所在服务")
	@Size(max = 100,message = "控制点所在服务长度超过100位")
	private String bpdSrvcode;

	@ApiModelProperty(value = "业务所在服务描述")
	@Size(max = 100,message = "业务所在服务描述长度超过100位")
	private String bpdSrvdesc;

	@ApiModelProperty(value = "状态:0无效，1有效")
	@NotNull(message = "状态必填")
	@Size(max = 1,message = "状态长度超过1位")
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
