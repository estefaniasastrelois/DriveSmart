package com.drivesmart.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.drivesmart.modelo.PreguntaVO;
import com.drivesmart.modelo.TestVO;

import jakarta.transaction.Transactional;

/**
 * Interfaz del repositorio para la entidad PreguntaVO.
 * Esta interfaz es utilizada por Spring Data JPA para crear una implementación del repositorio.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface PreguntaRepository extends JpaRepository<PreguntaVO, Integer>{
    /**
     * Busca una pregunta por enunciado y test.
     * 
     * @param enunciado El enunciado de la pregunta.
     * @param test El test de la pregunta.
     * @return Un Optional que puede contener la pregunta si se encuentra.
     */
    Optional<PreguntaVO> findByEnunciadoAndTest(String enunciado, TestVO test);
    
    /**
     * Busca preguntas por ID de test.
     * 
     * @param idtest El ID del test.
     * @return Una lista de preguntas para el test dado.
     */
    List<PreguntaVO> findByTestIdtest(int idtest);
    
    /**
     * Encuentra el ID de pregunta más grande.
     * 
     * @return El ID de pregunta más grande.
     */
    @Query("SELECT MAX(p.idpregunta) FROM PreguntaVO p")
    Integer findMaxIdPregunta();
    
    /**
     * Elimina todas las preguntas de un test.
     * 
     * @param idtest El ID del test.
     */
    @Modifying
    @Transactional
    @Query("delete from PreguntaVO p where p.test.idtest = :idtest")
    void deleteAllByIdtest(int idtest);
}