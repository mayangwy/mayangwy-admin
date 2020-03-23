package org.mayangwy.admin.modules.system.entity;

import lombok.Data;
import org.mayangwy.admin.core.base.enums.IsDelEnum;

import java.io.Serializable;

@Data
public class UserQueryDTO implements Serializable {

    private String userName;

    private String userNameLike;

    private String nickName;

    private Integer isDel = IsDelEnum.YES.getCode();

}