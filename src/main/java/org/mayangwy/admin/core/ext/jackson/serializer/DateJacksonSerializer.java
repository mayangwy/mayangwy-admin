package org.mayangwy.admin.core.ext.jackson.serializer;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class DateJacksonSerializer extends DateSerializer {

    public DateJacksonSerializer(){

    }

    public DateJacksonSerializer(Boolean useTimestamp, DateFormat customFormat) {
        super(useTimestamp, customFormat);
    }

    @Override
    public void serialize(Date value, JsonGenerator g, SerializerProvider provider) throws IOException {
        if (_asTimestamp(provider)) {
            g.writeNumber(_timestamp(value));
            return;
        } else {
            if(_customFormat == null){
                g.writeString(DateUtil.format(value, "yyyy-MM-dd HH:mm:ss"));
            } else {
                super.serialize(value, g, provider);
            }
        }
    }

    @Override
    public DateSerializer withFormat(Boolean timestamp, DateFormat customFormat) {
        return new DateJacksonSerializer(timestamp, customFormat);
    }

}