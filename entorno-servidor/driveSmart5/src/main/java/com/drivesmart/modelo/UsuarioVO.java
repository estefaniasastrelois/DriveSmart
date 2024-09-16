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
 * Clase de entidad que representa un usuario en la base de datos.
 * Esta clase es utilizada por JPA para mapear la tabla "usuarios".
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
@Table(name="usuarios")
public class UsuarioVO {
    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idusuario;
    
    /**
     * Nombre del usuario.
     */
    @NonNull
    private String nombre;
    
    /**
     * Apellidos del usuario.
     */
    @NonNull
    private String apellidos;
    
    /**
     * DNI del usuario, debe ser único.
     */
    @NonNull
    @Column(unique=true)
    private String dni;
    
    /**
     * Email del usuario.
     */
    @NonNull
    private String email;
    
    /**
     * Contraseña del usuario.
     */
    @NonNull
    private String password;
    
    /**
     * Teléfono del usuario.
     */
    @NonNull
    private String telefono;
    
    /**
     * Rol asociado al usuario.
     */
    @ManyToOne
    @NonNull
    @JoinColumn(name="idrol")
    private RolVO rol;
}