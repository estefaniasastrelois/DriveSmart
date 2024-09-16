package com.drivesmart.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drivesmart.modelo.UsuarioVO;

/**
 * Interfaz del repositorio para la entidad UsuarioVO.
 * Esta interfaz es utilizada por Spring Data JPA para crear una implementación del repositorio.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface UsuarioRepository extends JpaRepository<UsuarioVO, Integer>{
    /**
     * Busca un UsuarioVO por su DNI.
     * 
     * @param dni El DNI del usuario a buscar.
     * @return Un Optional que puede contener el UsuarioVO si se encuentra.
     * 
     */
    Optional<UsuarioVO> findByDni(String dni);

    /**
     * Busca un UsuarioVO por su email.
     * 
     * @param email El email del usuario a buscar.
     * @return Un Optional que puede contener el UsuarioVO si se encuentra.
     * 
     */
    Optional<UsuarioVO> findByEmail(String email);
}