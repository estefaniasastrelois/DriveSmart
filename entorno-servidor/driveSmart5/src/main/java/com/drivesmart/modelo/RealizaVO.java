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
 * Clase de entidad que representa una realización de test en la base de datos.
 * Esta clase es utilizada por JPA para mapear la tabla "realiza".
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
@Table(name="realiza",uniqueConstraints= {@UniqueConstraint(columnNames= {"idusuario","fechahora"})})
public class RealizaVO {
    /**
     * Identificador único de la realización.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idrealiza;
    
    /**
     * Usuario que realiza el test.
     */
    @NonNull
    @ManyToOne
    @JoinColumn(name="idusuario")
    private UsuarioVO usuario;
    
    /**
     * Test que se realiza.
     */
    @NonNull
    @ManyToOne
    @JoinColumn(name="idtest")
    private TestVO test;
    
    /**
     * Nota obtenida en el test.
     */
    @NonNull
    private Integer nota;
    
    /**
     * Fecha y hora de realización del test.
     */
    @NonNull
    private LocalDateTime fechahora;
}