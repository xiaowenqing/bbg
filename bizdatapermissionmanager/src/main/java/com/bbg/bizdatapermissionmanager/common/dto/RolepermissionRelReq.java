package com.bbg.bizdatapermissionmanager.common.dto;    /**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author xwq
 * @date 2020/3/11 001111:04
 */

import lombok.Data;

import java.util.List;

/**
 * 根据角色id集合+bpdCode(控制点编码)+dudCode(维度编码)查询出数据资源关联集合
 *   这个接口的入参
 * @author xwq
 * @create 2020-03-11 11:04
 **/
@Data
public class RolepermissionRelReq {

    private Integer[] roleIds;//角色id数组

    private String bpdCode;//控制点编码

    private String dudCode;//维度编码

    private List<String> dudCodeList;//维度编码集合
}
