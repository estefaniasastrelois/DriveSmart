package com.drivesmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación DriveSmart.
 * Esta clase inicia la aplicación utilizando Spring Boot.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@SpringBootApplication
public class DriveSmartApplication {

    /**
     * Método principal que inicia la aplicación.
     * 
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(DriveSmartApplication.class, args);
    }	
}