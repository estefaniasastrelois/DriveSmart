package com.drivesmart.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.drivesmart.modelo.OpcionVO;

import jakarta.transaction.Transactional;

/**
 * Interfaz del repositorio para la entidad OpcionVO.
 * Esta interfaz es utilizada por Spring Data JPA para crear una implementación del repositorio.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface OpcionRepository extends JpaRepository<OpcionVO, Integer> {
    /**
     * Busca opciones por ID de pregunta.
     * 
     * @param idpregunta El ID de la pregunta.
     * @return Una lista de opciones para la pregunta dada.
     */
    List<OpcionVO> findByPreguntaIdpregunta(int idpregunta);
    
    /**
     * Encuentra el ID de opción más grande.
     * 
     * @return El ID de opción más grande.
     */
    @Query("SELECT MAX(o.idopcion) FROM OpcionVO o")
    Integer findMaxIdOpcion();
    
    /**
     * Elimina todas las opciones de una pregunta.
     * 
     * @param idpregunta El ID de la pregunta.
     */
    @Modifying
    @Transactional
    @Query("delete from OpcionVO o where o.pregunta.idpregunta = :idpregunta")
    void deleteAllByIdpregunta(int idpregunta);
}