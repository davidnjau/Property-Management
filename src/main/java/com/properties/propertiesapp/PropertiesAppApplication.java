package com.properties.propertiesapp;

import com.properties.propertiesapp.authentication.entity.Role;
import com.properties.propertiesapp.authentication.service_class.impl.StaffDetailsServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PropertiesAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PropertiesAppApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*");            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner runner(StaffDetailsServiceImpl staffDetailsService){
        return args -> {
            staffDetailsService.saveRoles(new Role( "ROLE_ADMIN"));
            staffDetailsService.saveRoles(new Role( "ROLE_USER"));
        };
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
