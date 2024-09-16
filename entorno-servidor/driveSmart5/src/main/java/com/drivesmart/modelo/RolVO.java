package com.drivesmart.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Clase de entidad que representa un rol en la base de datos.
 * Esta clase es utilizada por JPA para mapear la tabla "roles".
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="roles")
public class RolVO {
    /**
     * Identificador único del rol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idrol;
    
    /**
     * Nombre del rol, debe ser único.
     */
    @NonNull
    @Column(unique=true)
    private String nombre;
}