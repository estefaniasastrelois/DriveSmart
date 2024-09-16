package com.drivesmart.modelo;

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
 * Clase de entidad que representa una opción en la base de datos.
 * Esta clase es utilizada por JPA para mapear la tabla "opciones".
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
@Table(name="opciones")
public class OpcionVO {
    /**
     * Identificador único de la opción.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idopcion;
    
    /**
     * Pregunta asociada a la opción.
     */
    @NonNull
    @ManyToOne
    @JoinColumn(name="idpregunta")
    private PreguntaVO pregunta;
    
    /**
     * Indica si la opción es correcta o no.
     */
    private int correcta;
    
    /**
     * Texto de la respuesta de la opción.
     */
    @NonNull
    private String respuesta;
}