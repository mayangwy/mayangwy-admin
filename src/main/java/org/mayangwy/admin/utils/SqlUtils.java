package org.mayangwy.admin.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;
import java.util.Date;

public class SqlUtils {

    private static final String QUESTION_MARK = "?";

    public static String mergeSqlAndParams(String sql, Object... objects){
        if(StrUtil.isBlank(sql)){
            return sql;
        }
        if(ArrayUtil.isEmpty(objects)){
            return sql;
        }

        StringBuilder stringBuilder = null;
        int startIndex = 0;
        int endIndex;
        for (Object object : objects) {
            endIndex = sql.indexOf(QUESTION_MARK, startIndex);
            if (endIndex == -1) {
                break;
            } else {
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder(sql.length() + 50);
                }
            }
            stringBuilder.append(sql, startIndex, endIndex);
            if (object == null) {
                stringBuilder.append("NULL");
            }
            if (object instanceof String) {
                stringBuilder.append('\'').append(object).append('\'');
            }
            if (object instanceof Date) {
                String dateStr = DateUtil.format((Date) object, "yyyy-MM-dd HH:mm:ss.SSS");
                stringBuilder.append('\'').append(dateStr).append('\'');
            }
            if (object instanceof BigDecimal) {
                stringBuilder.append(((BigDecimal) object).toPlainString());
            }
            if (object instanceof Byte || object instanceof Short || object instanceof Integer || object instanceof Long || object instanceof Double || object instanceof Float) {
                stringBuilder.append(object);
            }

            startIndex = endIndex + 1;

            if (startIndex == sql.length()) {
                break;
            }
        }

        if(stringBuilder != null){
            return stringBuilder.append(sql, startIndex, sql.length()).toString();
        } else {
            return sql;
        }

    }

    public static void main(String[] args) {
        //测试正常情况
        String sql0001 = "insert into table value(?, ? , ? ,?, ?,    ? , ?)";
        Object[] objects = new Object[]{1, new BigDecimal("1.2233212221332132132121323222121"), 234L, new Date(), "码云", 12.345, 3.14F};
        System.out.println(mergeSqlAndParams(sql0001, objects));

        //测试参数多的情况
        String sql0002 = "insert into table value(?, ? , ? ,?, ?,    ? , ?,?)";
        System.out.println(mergeSqlAndParams(sql0002, objects));

        //测试参数少的情况
        String sql0003 = "insert into table value(?, ? , ? ,?, ?,    ?)";
        System.out.println(mergeSqlAndParams(sql0003, objects));

        //测试问号在最后一位的情况
        String sql0004 = "insert into table value(?, ? , ? ,?, ?,    ?";
        System.out.println(mergeSqlAndParams(sql0004, objects));

        //测试一个问号的情况
        String sql0005 = "?";
        System.out.println(mergeSqlAndParams(sql0005, objects));

        System.out.println(NumberUtil.decimalFormat("##.##", 0.2304));
    }

}