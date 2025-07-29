package com.wherewasi.wherewasiapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.util.Collections;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient tmdbRestClient(@Value("${tmdb.apikey}") String apiKey) {
        ByteArrayHttpMessageConverter byteArrayConverter = new ByteArrayHttpMessageConverter();
        byteArrayConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
        return RestClient.builder()
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .messageConverters(converters -> {
                    converters.add(byteArrayConverter);
                })
                .build();
    }
}
