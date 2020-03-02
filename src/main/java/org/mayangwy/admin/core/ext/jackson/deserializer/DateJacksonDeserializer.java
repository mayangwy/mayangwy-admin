package org.mayangwy.admin.core.ext.jackson.deserializer;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateJacksonDeserializer extends DateDeserializers.DateDeserializer {

    private static final String[] patterns = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.S",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss.S",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm:ss.S",
            "yyyyMMdd", "yyyyMMdd HH:mm:ss", "yyyyMMdd HH:mm:ss.S",};

    public DateJacksonDeserializer(){

    }

    public DateJacksonDeserializer(DateDeserializers.DateDeserializer base, DateFormat df, String formatString) {
        super(base, df, formatString);
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken currentToken = p.getCurrentToken();
        if(JsonToken.VALUE_NUMBER_INT.equals(currentToken)){
            long longValue = p.getLongValue();
            return new Date(longValue);
        }

        String text = p.getText();
        if(text == null){
            return null;
        } else {
            text = text.trim();
        }

        if (_customFormat != null) {
            return DateUtil.parse(text, _formatString);
        } else {
            try {
                return DateUtils.parseDate(text, patterns);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected DateDeserializers.DateDeserializer withDateFormat(DateFormat df, String formatString) {
        return new DateJacksonDeserializer(this, df, formatString);
    }
}