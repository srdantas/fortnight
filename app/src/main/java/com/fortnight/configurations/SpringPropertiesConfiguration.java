package com.fortnight.configurations;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackages = "com.fortnight.gateways.properties")
public class SpringPropertiesConfiguration {
}
