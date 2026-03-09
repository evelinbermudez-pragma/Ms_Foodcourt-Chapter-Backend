package com.chapter.foodcourt.insfrastructure.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "roles")
@Getter
@Setter
public class RolesConfig {
    private Integer admin;
    private Integer owner;
    private Integer client;
    private Integer employee;
}
