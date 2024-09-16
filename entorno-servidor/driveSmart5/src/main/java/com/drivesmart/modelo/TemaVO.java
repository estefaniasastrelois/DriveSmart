package com.drivesmart.modelo;

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
 * Clase de entidad que representa un tema en la base de datos.
 * Esta clase es utilizada por JPA para mapear la tabla "temas".
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 3.0
 */
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="temas")
public class TemaVO {
    /**
     * Identificador único del tema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idtema;
    
    /**
     * Nombre del tema.
     */
    @NonNull
    private String nombre;
}