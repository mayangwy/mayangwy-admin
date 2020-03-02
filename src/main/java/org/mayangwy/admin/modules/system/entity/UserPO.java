package org.mayangwy.admin.modules.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.mayangwy.admin.core.base.annotation.StrTrim;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "SYS_USER")
@Entity
public class UserPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_NAME")
    @StrTrim(nullToEmptyStr = true)
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
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Column(name = "CREATE_USER_ID")
    private Long createUserId;

    @Column(name = "UPDATE_TIME")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Column(name = "UPDATE_USER_ID")
    private Long updateUserId;

    @Column(name = "IS_DEL")
    private Integer isDel;

    @Column(name = "POSITION_ID")
    private Long positionId;

    @Transient
    //@BigDecimalToFormatStr(pattern = "#0.000")
    private BigDecimal bbb;

    @Transient
    private Long ccc;

    @Transient
    private long ddd;

    @Transient
    private Double eee;

    @Transient
    private double fff;

    @Transient
    private List<String> strings = new ArrayList<>();

}