package com.mmerhav.remotecontrolserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties
public class RemoteControlServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RemoteControlServerApplication.class, args);
    }
}
