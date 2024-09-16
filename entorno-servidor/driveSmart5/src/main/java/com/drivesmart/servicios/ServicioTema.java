package com.drivesmart.servicios;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.drivesmart.modelo.TemaVO;

/**
 * Interfaz del servicio para la entidad TemaVO.
 * Esta interfaz define los métodos que deben ser implementados por el servicio de Tema.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 3.0
 */
public interface ServicioTema {

    /**
     * Busca un TemaVO por su nombre.
     * 
     * @param nombre El nombre del tema a buscar.
     * @return Un Optional que puede contener el TemaVO si se encuentra.
     * 
     */
    Optional<TemaVO> findByNombre(String nombre);

	<S extends TemaVO> S save(S entity);

	<S extends TemaVO> List<S> saveAll(Iterable<S> entities);

	<S extends TemaVO> Optional<S> findOne(Example<S> example);

	List<TemaVO> findAll(Sort sort);

	void flush();

	Page<TemaVO> findAll(Pageable pageable);

	<S extends TemaVO> S saveAndFlush(S entity);

	<S extends TemaVO> List<S> saveAllAndFlush(Iterable<S> entities);

	List<TemaVO> findAll();

	List<TemaVO> findAllById(Iterable<Integer> ids);

	void deleteInBatch(Iterable<TemaVO> entities);

	<S extends TemaVO> Page<S> findAll(Example<S> example, Pageable pageable);

	Optional<TemaVO> findById(Integer id);

	void deleteAllInBatch(Iterable<TemaVO> entities);

	boolean existsById(Integer id);

	<S extends TemaVO> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	<S extends TemaVO> boolean exists(Example<S> example);

	void deleteAllInBatch();

	TemaVO getOne(Integer id);

	<S extends TemaVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	long count();

	void deleteById(Integer id);

	TemaVO getById(Integer id);

	void delete(TemaVO entity);

	void deleteAllById(Iterable<? extends Integer> ids);

	TemaVO getReferenceById(Integer id);

	void deleteAll(Iterable<? extends TemaVO> entities);

	<S extends TemaVO> List<S> findAll(Example<S> example);

	<S extends TemaVO> List<S> findAll(Example<S> example, Sort sort);

	void deleteAll();

}