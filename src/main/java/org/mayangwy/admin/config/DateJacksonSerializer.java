//package org.mayangwy.admin.config;
//
//import cn.hutool.core.date.DateUtil;
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//
//import java.io.IOException;
//import java.util.Date;
//
//public class DateJacksonSerializer extends JsonSerializer<Date> {
//
//    private String dateFormat;
//
//    public void setDateFormat(String dateFormat) {
//        this.dateFormat = dateFormat;
//    }
//
//    @Override
//    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//        gen.writeString(DateUtil.format(value, dateFormat));
//    }
//
//    @Override
//    public Class<Date> handledType() {
//        return Date.class;
//    }
//}