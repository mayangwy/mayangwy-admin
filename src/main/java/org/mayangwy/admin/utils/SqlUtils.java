package org.mayangwy.admin.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
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

        boolean isInString = false;

        StringBuilder stringBuilder = null;
        int startIndex = 0;
        int endIndex;
        for (int i = 0; i < objects.length; i++) {
            endIndex = sql.indexOf(QUESTION_MARK, startIndex);
            if(endIndex == -1){
                if(startIndex == 0){
                    return sql;
                } else {
                    return stringBuilder.append(sql, startIndex + 1, sql.length()).toString();
                }
            } else {
                if(stringBuilder == null){
                    stringBuilder = new StringBuilder(sql.length() + 50);
                }
            }
            stringBuilder.append(sql, startIndex, endIndex);
            Object obj = objects[i];
            String objStr = null;
            if(obj == null){
                stringBuilder.append("NULL");
            }
            if(obj instanceof String){
                stringBuilder.append('\'').append(obj).append('\'');
            }
            if(obj instanceof Date){
                String dateStr = DateUtil.format((Date) obj, "yyyy-MM-dd HH:mm:ss.SSS");
                stringBuilder.append('\'').append(dateStr).append('\'');
            }
            if(obj instanceof BigDecimal){
                stringBuilder.append(((BigDecimal)obj).toPlainString());
            }
            if(obj instanceof Byte || obj instanceof Short || obj instanceof Integer || obj instanceof Long || obj instanceof Double || obj instanceof Float){
                stringBuilder.append(obj);
            }

            startIndex = endIndex + 1;
        }

        if(stringBuilder != null){
            stringBuilder.append(sql, startIndex, sql.length());
            return stringBuilder.toString();
        }

        return null;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            String string = "insert into table value(?, ? , ? ,?, ?,    ?    ,    ?)";
            //System.out.println(string.indexOf(QUESTION_MARK, 25));
            Object[] objects = new Object[]{1, new BigDecimal("1.2233212221332132132121323222121"), 234L, new Date(), "码云", 12.345, 3.14F};
            mergeSqlAndParams(string, objects);
            //System.out.println(mergeSqlAndParams(string, objects));;
        }
        System.out.println(System.currentTimeMillis() - start);
    }

}