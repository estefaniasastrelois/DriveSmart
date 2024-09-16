package com.drivesmart.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drivesmart.modelo.TestVO;

/**
 * Interfaz del repositorio para la entidad TestVO.
 * Esta interfaz es utilizada por Spring Data JPA para crear una implementación del repositorio.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface TestRepository extends JpaRepository<TestVO, Integer>{
    /**
     * Busca un TestVO por su referencia.
     * 
     * @param referencia La referencia del test a buscar.
     * @return Un Optional que puede contener el TestVO si se encuentra.
     * 
     */
    Optional<TestVO> findByReferencia(String referencia);

    /**
     * Busca una lista de TestVO por el nombre del permiso y el ámbito.
     * 
     * @param nombrePermiso El nombre del permiso a buscar.
     * @param nombreAmbito El nombre del ámbito a buscar.
     * @return Una lista de TestVO que coinciden con los criterios de búsqueda.
     * 
     */
    List<TestVO> findByPermisoNombreAndAmbito(String nombrePermiso, String nombreAmbito);
}