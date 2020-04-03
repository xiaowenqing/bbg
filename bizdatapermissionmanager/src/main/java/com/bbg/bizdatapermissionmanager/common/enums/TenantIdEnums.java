package com.bbg.bizdatapermissionmanager.common.enums;    /**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author xwq
 * @date 2020/3/16 001616:44
 */

import lombok.Getter;

/**
 * 租户ID 的枚举类
 * @author xwq
 * @create 2020-03-16 16:44
 **/
@Getter
public enum TenantIdEnums {
    BBG(1,"步步高集团"),
    FINISHED(2,"其他")
    ;

    private Integer code;

    private String message;

    TenantIdEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
