package com.drivesmart.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Clase de entidad que representa un test en la base de datos.
 * Esta clase es utilizada por JPA para mapear la tabla "test".
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
@Table(name="test")
public class TestVO {
    /**
     * Identificador único del test.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idtest;
    
    /**
     * Referencia del test, debe ser única.
     */
    @NonNull
    @Column(unique=true)
    private String referencia;
    
    /**
     * Ámbito del test.
     */
    @NonNull
    private String ambito;
    
    /**
     * Permiso asociado al test.
     */
    @NonNull
    @ManyToOne
    @JoinColumn(name="idpermiso")
    private PermisoVO permiso;
    
    /**
     * Tema asociado al test.
     */
    @ManyToOne
    @JoinColumn(name="idtema")
    private TemaVO tema;
}