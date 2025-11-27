package com.aayush.hrManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            	//User	
                .requestMatchers("/user/**").hasRole("ADMIN")
                //Employees
                .requestMatchers("/employees/show/**").hasAnyRole("EMPLOYEE","ADMIN")
                .requestMatchers(HttpMethod.GET, "/employees/**").hasAnyRole("MANAGER", "HR", "ADMIN")
                .requestMatchers("/employees/**").hasAnyRole("HR", "ADMIN")
                //Attendance
                .requestMatchers("/entry/checkIn/**","/entry/checkOut/**").hasAnyRole("HR", "ADMIN", "EMPLOYEE","MANAGER")
                .requestMatchers("/entry/showAll").hasAnyRole("HR", "ADMIN", "MANAGER")
                .requestMatchers("/entry/**").hasAnyRole("HR", "ADMIN")
                //Leave
                .requestMatchers("/leave/apply/**").hasAnyRole("HR", "ADMIN", "EMPLOYEE","MANAGER")
                .requestMatchers("/leave/records/**").hasAnyRole("HR", "ADMIN", "MANAGER")
                .requestMatchers("/leave/**").hasAnyRole("HR", "ADMIN")
                //Task
                .requestMatchers("/task/empViewTask").hasAnyRole("EMPLOYEE","HR", "ADMIN", "MANAGER")
                .requestMatchers("/task/addTask").hasAnyRole("HR", "ADMIN", "MANAGER")
                .requestMatchers("/task/**").hasAnyRole("HR", "ADMIN")
                
                .requestMatchers("/healthCheck/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

