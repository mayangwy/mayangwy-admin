package org.mayangwy.admin.core.config;

import org.mayangwy.admin.core.ext.jackson.deserializer.DateJacksonDeserializer;
import org.mayangwy.admin.core.ext.jackson.serializer.BigDecimalSerializer;
import org.mayangwy.admin.core.ext.jackson.serializer.DateJacksonSerializer;
import org.mayangwy.admin.core.ext.jackson.serializer.DoubleSerializer;
import org.mayangwy.admin.core.ext.jackson.serializer.LongSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Date;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customJackson() {
        return jacksonObjectMapperBuilder -> {
            //设置序列化类
            DoubleSerializer doubleSerializer = new DoubleSerializer();
            LongSerializer longSerializer = new LongSerializer();
            DateJacksonSerializer dateJacksonSerializer = new DateJacksonSerializer();
            BigDecimalSerializer bigDecimalSerializer = new BigDecimalSerializer();

            jacksonObjectMapperBuilder.serializerByType(Double.class, doubleSerializer)
                    .serializerByType(double.class, doubleSerializer)
                    .serializerByType(Long.class, longSerializer)
                    .serializerByType(long.class, longSerializer)
                    .serializerByType(Date.class, dateJacksonSerializer)
                    .serializerByType(BigDecimal.class, bigDecimalSerializer);

            //设置反序列化类
            DateJacksonDeserializer dateJacksonDeserializer = new DateJacksonDeserializer();
            jacksonObjectMapperBuilder.deserializerByType(Date.class, dateJacksonDeserializer);

        };
    }

}