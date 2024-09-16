package com.drivesmart.servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.drivesmart.modelo.PracticaVO;
import com.drivesmart.modelo.UsuarioVO;

/**
 * Interfaz del servicio para la entidad PracticaVO.
 * Esta interfaz define los métodos que deben ser implementados por el servicio de Practica.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface ServicioPractica {
	/**
	 * Busca una lista de PracticaVO por el ID del usuario, ordenadas por fecha y hora en orden ascendente.
	 * 
	 * @param idusuario El ID del usuario para buscar sus prácticas.
	 * @return Una lista de PracticaVO que pertenecen al usuario, ordenadas por fecha y hora en orden ascendente.
	 * 
	 */
	List<PracticaVO> findByUsuarioIdusuarioOrderByFechahoraAsc(int idusuario);

	/**
	 * Busca una lista de PracticaVO por un día específico.
	 * 
	 * @param day El día para buscar las prácticas.
	 * @return Una lista de PracticaVO que se realizaron en el día especificado.
	 * 
	 */
	List<PracticaVO> findPracticasByDay(LocalDateTime day);

	/**
	 * Obtiene una lista de PracticaVO por el ID del permiso.
	 * 
	 * @param idpermiso El ID del permiso para buscar sus prácticas.
	 * @return Una lista de PracticaVO que pertenecen al permiso.
	 * 
	 */
	List<PracticaVO> obtenerPracticasPorPermiso(int idpermiso);

	/**
	 * Obtiene una lista de PracticaVO por el ID del usuario y el ID del permiso.
	 * 
	 * @param idusuario El ID del usuario para buscar sus prácticas.
	 * @param idpermiso El ID del permiso para buscar sus prácticas.
	 * @return Una lista de PracticaVO que pertenecen al usuario y al permiso.
	 * 
	 */
	List<PracticaVO> getPracticasByUsuarioAndPermiso(int idusuario, int idpermiso);

	/**
	 * Elimina todas las PracticaVO de un usuario.
	 * 
	 * @param idusuario El ID del usuario para eliminar sus prácticas.
	 * 
	 */
	void eliminarPracticasDeUsuario(int idusuario);
	
	<S extends PracticaVO> S save(S entity);

	<S extends PracticaVO> List<S> saveAll(Iterable<S> entities);

	<S extends PracticaVO> Optional<S> findOne(Example<S> example);

	List<PracticaVO> findAll(Sort sort);

	void flush();

	Page<PracticaVO> findAll(Pageable pageable);

	<S extends PracticaVO> S saveAndFlush(S entity);

	<S extends PracticaVO> List<S> saveAllAndFlush(Iterable<S> entities);

	List<PracticaVO> findAll();

	List<PracticaVO> findAllById(Iterable<Integer> ids);

	void deleteInBatch(Iterable<PracticaVO> entities);

	<S extends PracticaVO> Page<S> findAll(Example<S> example, Pageable pageable);

	Optional<PracticaVO> findById(Integer id);

	void deleteAllInBatch(Iterable<PracticaVO> entities);

	boolean existsById(Integer id);

	<S extends PracticaVO> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	<S extends PracticaVO> boolean exists(Example<S> example);

	void deleteAllInBatch();

	PracticaVO getOne(Integer id);

	<S extends PracticaVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	long count();

	void deleteById(Integer id);

	PracticaVO getById(Integer id);

	void delete(PracticaVO entity);

	void deleteAllById(Iterable<? extends Integer> ids);

	PracticaVO getReferenceById(Integer id);

	void deleteAll(Iterable<? extends PracticaVO> entities);

	<S extends PracticaVO> List<S> findAll(Example<S> example);

	<S extends PracticaVO> List<S> findAll(Example<S> example, Sort sort);

	void deleteAll();

}