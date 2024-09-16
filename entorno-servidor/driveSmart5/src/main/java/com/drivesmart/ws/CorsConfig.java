package com.drivesmart.ws;
 
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
/**
 * Clase de configuración de CORS para permitir solicitudes desde diferentes orígenes.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 2.0
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
 
    /**
     * Configura las reglas de CORS para las solicitudes entrantes.
     * 
     * @param registry El registro de configuraciones de CORS.
     * 
     */
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        		.allowedOrigins("http://127.0.0.1:5501")
                .allowedOriginPatterns("*")  // Permitir todas las IPs
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Métodos permitidos
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}