package com.drivesmart.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drivesmart.modelo.TemaVO;

/**
 * Interfaz del repositorio para la entidad TemaVO.
 * Esta interfaz es utilizada por Spring Data JPA para crear una implementación del repositorio.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 3.0
 */
public interface TemaRepository extends JpaRepository<TemaVO, Integer>{
    /**
     * Busca un TemaVO por su nombre.
     * 
     * @param nombre El nombre del tema a buscar.
     * @return Un Optional que puede contener el TemaVO si se encuentra.
     * 
     */
    Optional<TemaVO> findByNombre(String nombre);
}