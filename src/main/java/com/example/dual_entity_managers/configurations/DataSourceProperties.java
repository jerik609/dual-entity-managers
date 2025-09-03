package com.example.dual_entity_managers.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

// https://docs.spring.io/spring-boot/docs/3.0.13-SNAPSHOT/api/org/springframework/boot/context/properties/ConstructorBinding.html
//@Component - not allowed!
public class DataSourceProperties {

    @ConfigurationProperties(prefix = "custom.unicorns2")
    public record FirstProperties(String url, String username, String password) {
    }

    @ConfigurationProperties(prefix = "custom.unicorns")
    public record SecondProperties(String url, String username, String password) {
    }

}
