package com.drivesmart.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drivesmart.modelo.PermisoVO;

/**
 * Interfaz del repositorio para la entidad PermisoVO.
 * Esta interfaz es utilizada por Spring Data JPA para crear una implementación del repositorio.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface PermisoRepository extends JpaRepository<PermisoVO, Integer>{
    /**
     * Busca un permiso por nombre.
     * 
     * @param nombre El nombre del permiso.
     * @return Un Optional que puede contener el permiso si se encuentra.
     */
    Optional<PermisoVO> findByNombre(String nombre);
}