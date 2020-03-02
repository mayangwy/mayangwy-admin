package org.mayangwy.admin.core.ext.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.math.BigDecimal;

public class DoubleSerializer extends StdSerializer<Double> {


    public DoubleSerializer() {
        super(Double.class);
    }

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if(value != null){
            gen.writeString(BigDecimal.valueOf(value).toPlainString());
        }
    }

}