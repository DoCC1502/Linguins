package at.ac.tgm.linguinsspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LinguinsSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinguinsSpringBootApplication.class, args);
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🚀 DOCKER COMPOSE TERMINAL SERVER");
        System.out.println("✅ HTTP: http://localhost:8080");
        System.out.println("✅ WebSocket: ws://localhost:8080/ws/terminal");
        System.out.println("🐳 Container: web-terminal-ubuntu");
        System.out.println("=".repeat(50) + "\n");
    }

    // In deiner Hauptklasse (LinguinsSpringBootApplication.java)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
