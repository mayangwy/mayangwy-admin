package org.mayangwy.admin.core.ext.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.mayangwy.admin.core.base.annotation.StrTrim;

import java.io.IOException;

public class StrTrimDeserializer extends StdDeserializer<String> implements ContextualDeserializer {

    private boolean isTrim;

    private boolean nullToEmptyStr;

    protected StrTrimDeserializer() {
        super(String.class);
    }

    public boolean isNullToEmptyStr() {
        return nullToEmptyStr;
    }

    public void setNullToEmptyStr(boolean nullToEmptyStr) {
        this.nullToEmptyStr = nullToEmptyStr;
    }

    public boolean isTrim() {
        return isTrim;
    }

    public void setTrim(boolean trim) {
        isTrim = trim;
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String text = p.getText();

        if(! isTrim){
            return text;
        }

        if(text == null){
            if(nullToEmptyStr){
                return "";
            } else {
                return null;
            }
        } else {
            return text.trim();
        }
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        StrTrim annotation = property.getAnnotation(StrTrim.class);
        if(annotation != null){
            StrTrimDeserializer strTrimDeserializer = new StrTrimDeserializer();
            strTrimDeserializer.setNullToEmptyStr(annotation.nullToEmptyStr());
            strTrimDeserializer.setTrim(true);
            return strTrimDeserializer;
        }
        return this;
    }

}