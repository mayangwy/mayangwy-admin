package org.mayangwy.admin.core.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * sql工具类 create at 2020/2/12
 * @author mayang
 *
 */
public class SqlUtils {

    private static final String QUESTION_MARK = "?";

    /**
     *
     * 合并sql和入参
     * @param sql
     * @param objects
     * @return
     *
     */
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

    public static void main(String[] args) throws IOException {
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

        System.out.println(Calendar.getInstance());

//        System.out.println(Long.valueOf("256181L"));

        //01100100
        File file = new File("D:\\document\\a.txt");
        File file2 = new File("D:/document/a.txt");
        System.out.println(file.exists());
        System.out.println(file2.exists());
        FileInputStream fileInputStream = new FileInputStream(file2);
        //BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
        //System.out.println(br.readLine());
        System.out.println("============================" + fileInputStream.available());
        System.out.println(fileInputStream.read() + "-" + fileInputStream.read() + "-" + fileInputStream.read() + "-" + fileInputStream.read());
        System.out.println();
        char c = 49;
        System.out.println(c);
        System.out.println(new String(new byte[]{(byte) 230, (byte) 136, (byte) 145}));
        //br.close();
        fileInputStream.close();

        //00000000 00000000 00000001 10000010
        //00000000 00000000 00000001 01111110
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\document\\a.txt");
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        dataOutputStream.writeInt(386);
        dataOutputStream.close();

        System.out.println(new BigDecimal(Double.MAX_VALUE).toPlainString());
        System.out.println(Double.valueOf(Float.MAX_VALUE));
        System.out.println(1113334454354335535111.000000000222 + "");
        System.out.println(String.valueOf(0.1));
        System.out.println(BigDecimal.valueOf(1113334454354335535111.000000000222).toPlainString());
        System.out.println(new BigDecimal(0.1));
        DecimalFormat df = new DecimalFormat("#0.00000000000");
        NumberFormat instance = NumberFormat.getInstance();
        instance.setGroupingUsed(false);
        System.out.println(instance.format(1113334454354335535111.000000000222));

        System.out.println(double.class);
        System.out.println(Double.MAX_VALUE);
        System.out.println(BigDecimal.valueOf(123456789009876.1).toPlainString());

        int i = 1;
        int i1 = i >> 8;
        System.out.println(i1);
        //00000000 00000000 00100111 00010000
        int b = 16+256*4+256*2+256+256*32;
        System.out.println(b);
        int a = '我';
        byte[] bytes = NumberUtil.toBytes(a);
        FileOutputStream outputStream = new FileOutputStream("D:\\document\\a.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
       // bufferedWriter.write(new String(bytes, StandardCharsets.UTF_8));
        bufferedWriter.write("我是说");
        bufferedWriter.flush();
        bufferedWriter.close();

        FileOutputStream outputStream2 = new FileOutputStream("D:\\document\\a.txt");
        DataOutputStream outputStream1 = new DataOutputStream(outputStream2);
        //outputStream2.write(384);
        outputStream1.writeChar('a');
        outputStream1.close();
        outputStream2.flush();
        outputStream2.close();

        String s = "a";
        System.out.println("a".getBytes(StandardCharsets.UTF_8).length);
    }

}