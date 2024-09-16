package com.drivesmart.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Clase de entidad que representa una práctica en la base de datos.
 * Esta clase es utilizada por JPA para mapear la tabla "practicas".
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
@Table(name="practicas",uniqueConstraints= {@UniqueConstraint(columnNames= {"fechahora","idusuario"})})
public class PracticaVO {
    /**
     * Identificador único de la práctica.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idpractica;
    
    /**
     * Fecha y hora de la práctica.
     */
    @NonNull
    private LocalDateTime fechahora;
    
    /**
     * Lugar donde se realiza la práctica.
     */
    @NonNull
    private String lugar;
    
    /**
     * Usuario que realiza la práctica.
     */
    @NonNull
    @ManyToOne
    @JoinColumn(name="idusuario")
    private UsuarioVO usuario;
    
    /**
     * Permiso asociado a la práctica.
     */
    @NonNull
    @ManyToOne
    @JoinColumn(name="idpermiso")
    private PermisoVO permiso;
}