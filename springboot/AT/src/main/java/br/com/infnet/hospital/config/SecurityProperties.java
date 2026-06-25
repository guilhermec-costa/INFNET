package br.com.infnet.hospital.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security.user")
public record SecurityProperties(
        String name,
        String password
) {
}
