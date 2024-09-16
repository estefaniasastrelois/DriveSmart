package com.drivesmart.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drivesmart.modelo.RolVO;

/**
 * Interfaz del repositorio para la entidad RolVO.
 * Esta interfaz es utilizada por Spring Data JPA para crear una implementación del repositorio.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface RolRepository extends JpaRepository<RolVO, Integer>{
	/**
     * Busca un RolVO por su nombre.
     * 
     * @param nombre El nombre del rol a buscar.
     * @return Un Optional que puede contener el RolVO si se encuentra.
     * 
     */
	Optional<RolVO> findByNombre(String nombre);
}
