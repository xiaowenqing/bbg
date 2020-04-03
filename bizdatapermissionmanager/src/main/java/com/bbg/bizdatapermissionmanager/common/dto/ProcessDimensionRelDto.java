package com.bbg.bizdatapermissionmanager.common.dto;    /**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author xwq
 * @date 2020/3/6 000616:16
 */

import com.bbg.bizdatapermissionmanager.entity.ProcessDimensionRelEntity;
import lombok.Data;

/**
 *
 * @author xwq
 * @create 2020-03-06 16:16
 **/
@Data
public class ProcessDimensionRelDto extends ProcessDimensionRelEntity {

    private Integer currentPage;

    private Integer pageSize;

    private Integer roleId;//角色编码

    private String isAssociated;//是否和角色关联，0否，1是

    private Integer rprId;//角色许可关系表编码
}
