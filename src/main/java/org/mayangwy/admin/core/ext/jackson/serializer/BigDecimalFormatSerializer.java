package org.mayangwy.admin.core.ext.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.mayangwy.admin.core.base.annotation.BigDecimalToFormatStr;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BigDecimalFormatSerializer extends StdSerializer<BigDecimal> implements ContextualSerializer {

    private DecimalFormat decimalFormat;

    public BigDecimalFormatSerializer() {
        super(BigDecimal.class);
    }


    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    public void setDecimalFormat(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        BigDecimalToFormatStr annotation = property.getAnnotation(BigDecimalToFormatStr.class);
        if(annotation != null){
            DecimalFormat decimalFormat = new DecimalFormat(annotation.pattern());
            decimalFormat.setRoundingMode(annotation.roundingMode());
            BigDecimalFormatSerializer serializer = new BigDecimalFormatSerializer();
            serializer.setDecimalFormat(decimalFormat);
            return serializer;
        }
        return this;
    }

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if(decimalFormat != null){
            gen.writeString(decimalFormat.format(value));
        }
    }

}