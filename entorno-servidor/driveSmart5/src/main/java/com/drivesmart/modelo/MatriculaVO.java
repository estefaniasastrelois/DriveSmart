package com.drivesmart.modelo;

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
 * Clase de entidad que representa una matrícula en la base de datos.
 * Esta clase es utilizada por JPA para mapear la tabla "matriculas".
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
@Table(name="matriculas",uniqueConstraints= {@UniqueConstraint(columnNames= {"idusuario","idpermiso"})})
public class MatriculaVO {
    /**
     * Identificador único de la matrícula.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idmatricula;
    
    /**
     * Usuario asociado a la matrícula.
     */
    @NonNull
    @ManyToOne
    @JoinColumn(name="idusuario")
    private UsuarioVO usuario;
    
    /**
     * Permiso asociado a la matrícula.
     */
    @NonNull
    @ManyToOne
    @JoinColumn(name="idpermiso")
    private PermisoVO permiso;
}