package com.bbg.bizdatapermissionmanager.common.enums;

import lombok.Getter;

/**
 * 状态的枚举
 * @author xwq
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2019/12/29 002910:43
 */
@Getter
public enum StatusEnums {
    NEW("0","无效"),
    FINISHED("1","有效")
    ;

    private String code;

    private String message;

    StatusEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
