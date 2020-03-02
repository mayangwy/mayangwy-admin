package org.mayangwy.admin.modules.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "sys_operate_log")
@Entity
public class SysOperateLogPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "trace_id")
    private Long traceId;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "class_name")
    private String className;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "client_ip")
    private String clientIp;

    @Column(name = "opt_status")
    private Integer optStatus;

    @Column(name = "error_type")
    private Integer errorType;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "cost_time")
    private BigDecimal costTime;

    @Column(name = "is_del")
    private Integer isDel;

}