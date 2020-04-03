package com.bbg.bizdatapermissionmanager.common.dto;    /**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author xwq
 * @date 2020/3/7 000715:32
 */

import com.bbg.bizdatapermissionmanager.entity.RolepermissionRelEntity;
import lombok.Data;

/**
 *
 * @author xwq
 * @create 2020-03-07 15:32
 **/
@Data
public class RolepermissionRelDto extends RolepermissionRelEntity{
    private Integer currentPage;

    private Integer pageSize;


}
