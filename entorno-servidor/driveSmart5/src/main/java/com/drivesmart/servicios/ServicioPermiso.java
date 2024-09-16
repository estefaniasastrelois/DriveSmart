package com.drivesmart.servicios;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.drivesmart.modelo.PermisoVO;

/**
 * Interfaz del servicio para la entidad PermisoVO.
 * Esta interfaz define los métodos que deben ser implementados por el servicio de Permiso.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface ServicioPermiso {

    /**
     * Busca un PermisoVO por su nombre.
     * 
     * @param nombre El nombre del permiso a buscar.
     * @return Un Optional que puede contener el PermisoVO si se encuentra.
     * 
     */
    Optional<PermisoVO> findByNombre(String nombre);

	<S extends PermisoVO> S save(S entity);

	<S extends PermisoVO> List<S> saveAll(Iterable<S> entities);

	<S extends PermisoVO> Optional<S> findOne(Example<S> example);

	List<PermisoVO> findAll(Sort sort);

	void flush();

	Page<PermisoVO> findAll(Pageable pageable);

	<S extends PermisoVO> S saveAndFlush(S entity);

	<S extends PermisoVO> List<S> saveAllAndFlush(Iterable<S> entities);

	List<PermisoVO> findAll();

	List<PermisoVO> findAllById(Iterable<Integer> ids);

	void deleteInBatch(Iterable<PermisoVO> entities);

	<S extends PermisoVO> Page<S> findAll(Example<S> example, Pageable pageable);

	Optional<PermisoVO> findById(Integer id);

	void deleteAllInBatch(Iterable<PermisoVO> entities);

	boolean existsById(Integer id);

	<S extends PermisoVO> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	<S extends PermisoVO> boolean exists(Example<S> example);

	void deleteAllInBatch();

	PermisoVO getOne(Integer id);

	<S extends PermisoVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	long count();

	void deleteById(Integer id);

	PermisoVO getById(Integer id);

	void delete(PermisoVO entity);

	void deleteAllById(Iterable<? extends Integer> ids);

	PermisoVO getReferenceById(Integer id);

	void deleteAll(Iterable<? extends PermisoVO> entities);

	<S extends PermisoVO> List<S> findAll(Example<S> example);

	<S extends PermisoVO> List<S> findAll(Example<S> example, Sort sort);

	void deleteAll();

}