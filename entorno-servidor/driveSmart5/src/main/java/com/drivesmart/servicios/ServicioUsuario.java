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
import com.drivesmart.modelo.UsuarioVO;

/**
 * Interfaz del servicio para la entidad UsuarioVO.
 * Esta interfaz define los métodos que deben ser implementados por el servicio de Usuario.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface ServicioUsuario {

    /**
     * Busca un UsuarioVO por su DNI.
     * 
     * @param dni El DNI del usuario a buscar.
     * @return Un Optional que puede contener el UsuarioVO si se encuentra.
     * 
     */
    Optional<UsuarioVO> findByDni(String dni);
    
    /**
     * Busca un UsuarioVO por su email.
     * 
     * @param email El email del usuario a buscar.
     * @return Un Optional que puede contener el UsuarioVO si se encuentra.
     * 
     */
    Optional<UsuarioVO> findByEmail(String email);
	
	<S extends UsuarioVO> S save(S entity);

	<S extends UsuarioVO> List<S> saveAll(Iterable<S> entities);

	<S extends UsuarioVO> Optional<S> findOne(Example<S> example);

	List<UsuarioVO> findAll(Sort sort);

	void flush();

	Page<UsuarioVO> findAll(Pageable pageable);

	<S extends UsuarioVO> S saveAndFlush(S entity);

	<S extends UsuarioVO> List<S> saveAllAndFlush(Iterable<S> entities);

	List<UsuarioVO> findAll();

	List<UsuarioVO> findAllById(Iterable<Integer> ids);

	void deleteInBatch(Iterable<UsuarioVO> entities);

	<S extends UsuarioVO> Page<S> findAll(Example<S> example, Pageable pageable);

	Optional<UsuarioVO> findById(Integer id);

	void deleteAllInBatch(Iterable<UsuarioVO> entities);

	boolean existsById(Integer id);

	<S extends UsuarioVO> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	<S extends UsuarioVO> boolean exists(Example<S> example);

	void deleteAllInBatch();

	UsuarioVO getOne(Integer id);

	<S extends UsuarioVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	long count();

	void deleteById(Integer id);

	UsuarioVO getById(Integer id);

	void delete(UsuarioVO entity);

	void deleteAllById(Iterable<? extends Integer> ids);

	UsuarioVO getReferenceById(Integer id);

	void deleteAll(Iterable<? extends UsuarioVO> entities);

	<S extends UsuarioVO> List<S> findAll(Example<S> example);

	<S extends UsuarioVO> List<S> findAll(Example<S> example, Sort sort);

	void deleteAll();

}