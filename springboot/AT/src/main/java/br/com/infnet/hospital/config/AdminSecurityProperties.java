package br.com.infnet.hospital.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security.admin")
public record AdminSecurityProperties(
        String name,
        String password
) {
}
