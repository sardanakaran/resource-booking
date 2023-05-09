package com.osttra.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.osttra.resource.model.Resource;
import com.osttra.resource.repository.ResourceRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
@Configuration
@EnableWebMvc
public class OsttraSeatBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OsttraSeatBookingApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        return mapper;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("Content-Type");
            }
        };
    }

    @Bean
    ApplicationRunner init(ResourceRepository seatRepository) {
        return args -> {
            Resource seat = new Resource();
            seat.setName("9F-101");
            seat.setDescription("Testing 1");
            seat.setDocStationEnabled(true);

            Resource seat2 = new Resource();
            seat2.setName("9F-102");
            seat2.setDescription("Testing 2");
            seat2.setDocStationEnabled(false);

            Resource seat3 = new Resource();
            seat3.setName("9F-103");
            seat3.setDescription("Testing 3");
            seat3.setDocStationEnabled(false);

            seatRepository.saveAll(Arrays.asList(seat, seat2, seat3));

        };
    }

}
