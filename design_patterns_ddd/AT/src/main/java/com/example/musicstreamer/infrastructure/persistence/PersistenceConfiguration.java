package com.example.musicstreamer.infrastructure.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class PersistenceConfiguration {

    @Bean
    public Clock systemClock() {
        return Clock.systemUTC();
    }
}
