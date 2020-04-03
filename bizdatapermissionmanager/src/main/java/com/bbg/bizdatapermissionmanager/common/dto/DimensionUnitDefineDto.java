package com.bbg.bizdatapermissionmanager.common.dto;    /**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author xwq
 * @date 2020/3/5 000518:07
 */

import com.bbg.bizdatapermissionmanager.entity.DimensionUnitDefineEntity;
import lombok.Data;

/**
 * 元素（维度）定义(基础资源，被授权的分类信息) 的分页参数接收类
 * @author xwq
 * @create 2020-03-05 18:07
 **/
@Data
public class DimensionUnitDefineDto extends DimensionUnitDefineEntity {
    private Integer currentPage;

    private Integer pageSize;

    private String bpdCode;//控制点编码

    private String isAssociated;//是否和控制点关联，0否，1是

}
