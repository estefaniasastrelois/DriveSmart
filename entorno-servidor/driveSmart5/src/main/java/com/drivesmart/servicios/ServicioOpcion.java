package com.drivesmart.servicios;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.drivesmart.modelo.OpcionVO;
import com.drivesmart.modelo.PreguntaVO;

/**
 * Interfaz del servicio para la entidad OpcionVO.
 * Esta interfaz define los métodos que deben ser implementados por el servicio de Opcion.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface ServicioOpcion {

    /**
     * Busca una lista de OpcionVO por el ID de la pregunta.
     * 
     * @param idpregunta El ID de la pregunta para buscar sus opciones.
     * @return Una lista de OpcionVO que pertenecen a la pregunta.
     * 
     */
    List<OpcionVO> findByPreguntaIdpregunta(int idpregunta);

    /**
     * Encuentra el ID máximo entre las OpcionVO.
     * 
     * @return El ID máximo encontrado.
     * 
     */
    Integer findMaxIdOpcion(); 

    /**
     * Elimina todas las OpcionVO de una pregunta.
     * 
     * @param idpregunta El ID de la pregunta para eliminar sus opciones.
     * 
     */
    void eliminarOpcionesDePregunta(int idpregunta);
	
	<S extends OpcionVO> S save(S entity);

	<S extends OpcionVO> List<S> saveAll(Iterable<S> entities);

	<S extends OpcionVO> Optional<S> findOne(Example<S> example);

	List<OpcionVO> findAll(Sort sort);

	void flush();

	Page<OpcionVO> findAll(Pageable pageable);

	<S extends OpcionVO> S saveAndFlush(S entity);

	<S extends OpcionVO> List<S> saveAllAndFlush(Iterable<S> entities);

	List<OpcionVO> findAll();

	List<OpcionVO> findAllById(Iterable<Integer> ids);

	void deleteInBatch(Iterable<OpcionVO> entities);

	<S extends OpcionVO> Page<S> findAll(Example<S> example, Pageable pageable);

	Optional<OpcionVO> findById(Integer id);

	void deleteAllInBatch(Iterable<OpcionVO> entities);

	boolean existsById(Integer id);

	<S extends OpcionVO> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	<S extends OpcionVO> boolean exists(Example<S> example);

	void deleteAllInBatch();

	OpcionVO getOne(Integer id);

	<S extends OpcionVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	long count();

	void deleteById(Integer id);

	OpcionVO getById(Integer id);

	void delete(OpcionVO entity);

	void deleteAllById(Iterable<? extends Integer> ids);

	OpcionVO getReferenceById(Integer id);

	void deleteAll(Iterable<? extends OpcionVO> entities);

	<S extends OpcionVO> List<S> findAll(Example<S> example);

	<S extends OpcionVO> List<S> findAll(Example<S> example, Sort sort);

	void deleteAll();

}