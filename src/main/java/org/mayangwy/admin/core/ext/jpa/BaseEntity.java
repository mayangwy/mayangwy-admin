package org.mayangwy.admin.core.ext.jpa;

import lombok.Data;
import org.mayangwy.admin.core.base.annotation.CreateTime;
import org.mayangwy.admin.core.base.annotation.IsDel;
import org.mayangwy.admin.core.base.annotation.UpdateTime;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    @CreateTime
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "CREATE_USER_ID")
    private Long createUserId;

    @UpdateTime
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "UPDATE_USER_ID")
    private Long updateUserId;

    @IsDel
    @Column(name = "IS_DEL")
    private Integer isDel;

}