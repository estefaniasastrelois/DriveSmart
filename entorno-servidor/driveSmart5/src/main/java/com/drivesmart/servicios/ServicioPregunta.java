package com.drivesmart.servicios;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.drivesmart.modelo.PreguntaVO;
import com.drivesmart.modelo.TestVO;

/**
 * Interfaz del servicio para la entidad PreguntaVO.
 * Esta interfaz define los métodos que deben ser implementados por el servicio de Pregunta.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface ServicioPregunta {

    /**
     * Busca una PreguntaVO por su enunciado y TestVO.
     * 
     * @param enunciado El enunciado de la pregunta a buscar.
     * @param test El TestVO de la pregunta a buscar.
     * @return Un Optional que puede contener la PreguntaVO si se encuentra.
     * 
     */
    Optional<PreguntaVO> findByEnunciadoAndTest(String enunciado, TestVO test);

    /**
     * Busca una lista de PreguntaVO por el ID del test.
     * 
     * @param idtest El ID del test para buscar sus preguntas.
     * @return Una lista de PreguntaVO que pertenecen al test.
     * 
     */
    List<PreguntaVO> findByTestIdtest(int idtest);

    /**
     * Encuentra el ID máximo entre las PreguntaVO.
     * 
     * @return El ID máximo encontrado.
     * 
     */
    Integer findMaxIdPregunta(); 

    /**
     * Elimina todas las PreguntaVO de un test.
     * 
     * @param idtest El ID del test para eliminar sus preguntas.
     * 
     */
    void eliminarPreguntasDeTest(int idtest);
	
	<S extends PreguntaVO> S save(S entity);

	<S extends PreguntaVO> List<S> saveAll(Iterable<S> entities);

	<S extends PreguntaVO> Optional<S> findOne(Example<S> example);

	List<PreguntaVO> findAll(Sort sort);

	void flush();

	Page<PreguntaVO> findAll(Pageable pageable);

	<S extends PreguntaVO> S saveAndFlush(S entity);

	<S extends PreguntaVO> List<S> saveAllAndFlush(Iterable<S> entities);

	List<PreguntaVO> findAll();

	List<PreguntaVO> findAllById(Iterable<Integer> ids);

	void deleteInBatch(Iterable<PreguntaVO> entities);

	<S extends PreguntaVO> Page<S> findAll(Example<S> example, Pageable pageable);

	Optional<PreguntaVO> findById(Integer id);

	void deleteAllInBatch(Iterable<PreguntaVO> entities);

	boolean existsById(Integer id);

	<S extends PreguntaVO> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	<S extends PreguntaVO> boolean exists(Example<S> example);

	void deleteAllInBatch();

	PreguntaVO getOne(Integer id);

	<S extends PreguntaVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	long count();

	void deleteById(Integer id);

	PreguntaVO getById(Integer id);

	void delete(PreguntaVO entity);

	void deleteAllById(Iterable<? extends Integer> ids);

	PreguntaVO getReferenceById(Integer id);

	void deleteAll(Iterable<? extends PreguntaVO> entities);

	<S extends PreguntaVO> List<S> findAll(Example<S> example);

	<S extends PreguntaVO> List<S> findAll(Example<S> example, Sort sort);

	void deleteAll();

}