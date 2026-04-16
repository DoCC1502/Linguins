package at.ac.tgm.linguinsspringboot.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF deaktivieren
                .csrf(csrf -> csrf.disable())

                // 2. Zugriffsberechtigungen definieren
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html", "/assets/**", "/favicon.ico", "/linux-simulator.html").permitAll()
                        .requestMatchers("/api/users/**").permitAll()
                        .anyRequest().permitAll()
                )

                // 3. Das Standard-Formular deaktivieren
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                // 4. NEU: Erlaube Iframes von der gleichen Domain (SameOrigin)
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                );

        return http.build();
    }
}