package com.example.dual_entity_managers.database;

import org.springframework.boot.context.properties.ConfigurationProperties;

public class DataSourceProperties {

    @ConfigurationProperties(prefix = "custom.first")
    public record FirstProperties(String url, String username, String password) {
    }

    @ConfigurationProperties(prefix = "custom.second")
    public record SecondProperties(String url, String username, String password) {
    }

}
