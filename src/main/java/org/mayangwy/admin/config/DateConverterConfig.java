package org.mayangwy.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class DateConverterConfig {

    @Value("spring.jackson.date-format")
    private String dateFormat;

    @Bean
    public DateJacksonDeserializer dateJacksonConverter() {
        return new DateJacksonDeserializer();
    }

    @Bean
    @ConfigurationProperties("spring.jackson")
    public DateJacksonSerializer dateJacksonSerializer() {
        DateJacksonSerializer dateJacksonSerializer = new DateJacksonSerializer();
        dateJacksonSerializer.setDateFormat(dateFormat);
        return dateJacksonSerializer;
    }

    @Bean
    public Jackson2ObjectMapperFactoryBean jackson2ObjectMapperFactoryBean(@Autowired DateJacksonDeserializer dateJacksonDeserializer, @Autowired DateJacksonSerializer dateJacksonSerializer) {
        Jackson2ObjectMapperFactoryBean jackson2ObjectMapperFactoryBean = new Jackson2ObjectMapperFactoryBean();

        jackson2ObjectMapperFactoryBean.setDeserializers(dateJacksonDeserializer);
        jackson2ObjectMapperFactoryBean.setSerializers(dateJacksonSerializer);
        return jackson2ObjectMapperFactoryBean;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(
            @Autowired
                    ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
                new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        return mappingJackson2HttpMessageConverter;
    }

}