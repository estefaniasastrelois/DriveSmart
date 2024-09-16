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
 * Clase de entidad que representa una pregunta en la base de datos.
 * Esta clase es utilizada por JPA para mapear la tabla "preguntas".
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
@Table(name="preguntas")
public class PreguntaVO {
    /**
     * Identificador único de la pregunta.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idpregunta;
    
    /**
     * Enunciado de la pregunta.
     */
    @NonNull
    private String enunciado;
    
    /**
     * Foto asociada a la pregunta.
     */
    private String foto;
    
    /**
     * Test al que pertenece la pregunta.
     */
    @NonNull
    @ManyToOne
    @JoinColumn(name="idtest")
    private TestVO test;
}