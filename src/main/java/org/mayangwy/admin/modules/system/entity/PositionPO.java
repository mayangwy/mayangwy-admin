package org.mayangwy.admin.modules.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "SYS_POSITION")
@Entity
public class PositionPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "POSITION_NAME")
    private String positionName;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "CREATE_USER_ID")
    private Long createUserId;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "UPDATE_USER_ID")
    private Long updateUserId;

    @Column(name = "IS_DEL")
    private Integer isDel;

}