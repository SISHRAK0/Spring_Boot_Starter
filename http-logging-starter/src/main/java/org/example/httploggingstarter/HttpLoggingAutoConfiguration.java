package org.example.httploggingstarter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "httplogging", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(HttpLoggingProperties.class)
public class HttpLoggingAutoConfiguration {

    @Bean
    public HttpLoggingAspect httpLoggingAspect(HttpLoggingProperties properties) {
        return new HttpLoggingAspect(properties);
    }
}
