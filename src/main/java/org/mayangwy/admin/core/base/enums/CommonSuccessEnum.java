package org.mayangwy.admin.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonSuccessEnum implements ISuccessResultCode {

    SUCCESS(CommonSuccessEnum.CODE_SUCCESS, null),
    SUCCESS_OPERATE(CommonSuccessEnum.CODE_SUCCESS_OPERATE, CommonSuccessEnum.MSG_SUCCESS_OPERATE),
    SUCCESS_SAVE(CommonSuccessEnum.CODE_SUCCESS_SAVE, CommonSuccessEnum.MSG_SUCCESS_SAVE),
    SUCCESS_ADD(CommonSuccessEnum.CODE_SUCCESS_ADD, CommonSuccessEnum.MSG_SUCCESS_ADD),
    SUCCESS_MODIFY(CommonSuccessEnum.CODE_SUCCESS_MODIFY, CommonSuccessEnum.MSG_SUCCESS_MODIFY),
    SUCCESS_UPDATE(CommonSuccessEnum.CODE_SUCCESS_UPDATE, CommonSuccessEnum.MSG_SUCCESS_UPDATE),
    SUCCESS_DELETE(CommonSuccessEnum.CODE_SUCCESS_DELETE, CommonSuccessEnum.MSG_SUCCESS_DELETE);

    public static final int CODE_SUCCESS = 1000;
    public static final int CODE_SUCCESS_OPERATE = 1001;
    public static final int CODE_SUCCESS_SAVE = 1002;
    public static final int CODE_SUCCESS_ADD = 1003;
    public static final int CODE_SUCCESS_MODIFY = 1004;
    public static final int CODE_SUCCESS_UPDATE = 1005;
    public static final int CODE_SUCCESS_DELETE = 1006;

    public static final String MSG_SUCCESS_OPERATE = "操作成功";
    public static final String MSG_SUCCESS_SAVE = "保存成功";
    public static final String MSG_SUCCESS_ADD = "添加成功";
    public static final String MSG_SUCCESS_MODIFY = "修改成功";
    public static final String MSG_SUCCESS_UPDATE = "更新成功";
    public static final String MSG_SUCCESS_DELETE = "删除成功";

    private Integer resultCode;
    private String msg;

}