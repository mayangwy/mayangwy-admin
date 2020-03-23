package org.mayangwy.admin.core.base.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageOutput<T> implements Serializable {

    /**
     * 总条目
     */
    private Long count = -1L;

    /**
     * 结果集
     */
    private List<T> list;

}