package com.bbg.bizdatapermissionmanager.common.dto;    /**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author xwq
 * @date 2020/3/5 000517:10
 */

import com.bbg.bizdatapermissionmanager.entity.BizscopeProcessDefineEntity;
import lombok.Data;

/**
 * 业务模块分页查询的参数接收类
 * @author xwq
 * @create 2020-03-05 17:10
 **/
@Data
public class BizscopeProcessDefineDto extends BizscopeProcessDefineEntity {

    private Integer currentPage;

    private Integer pageSize;
}
