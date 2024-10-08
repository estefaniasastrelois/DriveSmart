package com.drivesmart.servicios;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.drivesmart.modelo.RolVO;

/**
 * Interfaz del servicio para la entidad RolVO.
 * Esta interfaz define los métodos que deben ser implementados por el servicio de Rol.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface ServicioRol {

    /**
     * Busca un RolVO por su nombre.
     * 
     * @param nombre El nombre del rol a buscar.
     * @return Un Optional que puede contener el RolVO si se encuentra.
     * 
     */
    Optional<RolVO> findByNombre(String nombre);

	<S extends RolVO> S save(S entity);

	<S extends RolVO> List<S> saveAll(Iterable<S> entities);

	<S extends RolVO> Optional<S> findOne(Example<S> example);

	List<RolVO> findAll(Sort sort);

	void flush();

	Page<RolVO> findAll(Pageable pageable);

	<S extends RolVO> S saveAndFlush(S entity);

	<S extends RolVO> List<S> saveAllAndFlush(Iterable<S> entities);

	List<RolVO> findAll();

	List<RolVO> findAllById(Iterable<Integer> ids);

	void deleteInBatch(Iterable<RolVO> entities);

	<S extends RolVO> Page<S> findAll(Example<S> example, Pageable pageable);

	Optional<RolVO> findById(Integer id);

	void deleteAllInBatch(Iterable<RolVO> entities);

	boolean existsById(Integer id);

	<S extends RolVO> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	<S extends RolVO> boolean exists(Example<S> example);

	void deleteAllInBatch();

	RolVO getOne(Integer id);

	<S extends RolVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	long count();

	void deleteById(Integer id);

	RolVO getById(Integer id);

	void delete(RolVO entity);

	void deleteAllById(Iterable<? extends Integer> ids);

	RolVO getReferenceById(Integer id);

	void deleteAll(Iterable<? extends RolVO> entities);

	<S extends RolVO> List<S> findAll(Example<S> example);

	<S extends RolVO> List<S> findAll(Example<S> example, Sort sort);

	void deleteAll();

}