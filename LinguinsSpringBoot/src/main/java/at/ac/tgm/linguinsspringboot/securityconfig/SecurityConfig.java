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
                // 1. CSRF deaktivieren (wichtig für API-Tests ohne Token)
                .csrf(csrf -> csrf.disable())

                // 2. Zugriffsberechtigungen definieren
                .authorizeHttpRequests(auth -> auth
                        // Erlaube statische Dateien (Vue Build)
                        .requestMatchers("/", "/index.html", "/assets/**", "/favicon.ico").permitAll()
                        // Erlaube deine API-Endpunkte
                        .requestMatchers("/api/users/**").permitAll()
                        // Alles andere erfordert Login (oder du setzt auch hier .permitAll())
                        .anyRequest().permitAll()
                )

                // 3. Das Standard-Formular deaktivieren
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}