package com.MindHub.Homebanking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Agrega solo la URL del frontend desplegado
        configuration.setAllowedOrigins(List.of("https://homebanking-react-c55-mh-luzmieres.onrender.com","http://localhost:5173"));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));

        // Permitir todas las cabeceras
        configuration.setAllowedHeaders(List.of("*"));

        // Permitir envío de credenciales como cookies o encabezados de autenticación
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Aplica esta configuración a todas las rutas
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
