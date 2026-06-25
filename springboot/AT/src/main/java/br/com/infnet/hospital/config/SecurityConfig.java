package br.com.infnet.hospital.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableConfigurationProperties({SecurityProperties.class, AdminSecurityProperties.class})
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/actuator/env/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/medicos/**").authenticated()
                        .requestMatchers("/actuator/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(
            SecurityProperties securityProperties,
            AdminSecurityProperties adminSecurityProperties,
            PasswordEncoder passwordEncoder
    ) {
        UserDetails appUser = User.builder()
                .username(securityProperties.name())
                .password(passwordEncoder.encode(securityProperties.password()))
                .roles("USER")
                .build();

        UserDetails adminUser = User.builder()
                .username(adminSecurityProperties.name())
                .password(passwordEncoder.encode(adminSecurityProperties.password()))
                .roles("ADMIN", "USER")
                .build();

        return new InMemoryUserDetailsManager(appUser, adminUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
