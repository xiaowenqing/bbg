package com.bbg.bizdatapermissionmanager.common.dto;    /**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author xwq
 * @date 2020/3/6 000616:29
 */

import lombok.Data;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xwq
 * @create 2020-03-06 16:29
 **/
@Data
public class ListWrapper<E> extends ArrayList<E>{
    @Valid
    private List<E> list;

    public ListWrapper() {
        list = new ArrayList<>();
    }

    public  ListWrapper(List<E> list) {
        this.list = list;
    }

}
