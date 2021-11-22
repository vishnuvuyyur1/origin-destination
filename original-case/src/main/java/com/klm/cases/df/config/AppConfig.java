package com.klm.cases.df.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {
	@Value("${server.cors.mapping}")
	String mapping;
	@Value("#{'${server.cors.allowedorigins}'.split(',')}")
	String[] allowedOrigins;
	@Value("#{'${server.cors.allowedmethods}'.split(',')}")
	String[] allowedMethods;
	@Value("#{'${server.cors.allowedheaders}'.split(',')}")
	String[] allowedHeaders;
	
    @Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping(mapping).allowedMethods(allowedMethods).allowedOrigins(allowedOrigins)
						.allowedHeaders(allowedHeaders).allowCredentials(true);
			}
		};
	}
    
    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }
}
