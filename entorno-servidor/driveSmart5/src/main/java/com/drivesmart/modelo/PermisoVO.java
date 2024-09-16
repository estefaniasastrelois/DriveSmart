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
 * Clase de entidad que representa un permiso en la base de datos.
 * Esta clase es utilizada por JPA para mapear la tabla "permisos".
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
@Table(name="permisos")
public class PermisoVO {
    /**
     * Identificador único del permiso.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idpermiso;
    
    /**
     * Nombre del permiso, debe ser único.
     */
    @NonNull
    @Column(unique=true)
    private String nombre;
    
    /**
     * Descripción del permiso.
     */
    @NonNull
    private String descripcion;
}