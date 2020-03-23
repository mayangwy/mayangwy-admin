package org.mayangwy.admin.core.base.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageInput implements Serializable {

    /**
     * 当前页码
     */
    private Integer currentPage = 0;

    /**
     * 每页条数
     */
    private Integer pageSize = 10;

    /**
     * 是否统计总条目
     */
    private boolean isCount = true;

}