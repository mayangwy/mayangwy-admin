package org.mayangwy.admin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "SYS_USER")
@Entity
public class UserPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "ID_CARD_NO")
    private String idCardNo;

    @Column(name = "DEPARTMENT_ID")
    private Long departmentId;

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