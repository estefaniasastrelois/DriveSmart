package com.drivesmart.servicios;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.drivesmart.modelo.MatriculaVO;
import com.drivesmart.modelo.PermisoVO;
import com.drivesmart.modelo.UsuarioVO;

/**
 * Interfaz del servicio para la entidad MatriculaVO.
 * Esta interfaz define los métodos que deben ser implementados por el servicio de Matricula.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface ServicioMatricula {
	/**
     * Busca una MatriculaVO por su UsuarioVO y PermisoVO.
     * 
     * @param usuario El UsuarioVO de la matrícula a buscar.
     * @param tipocarnet El PermisoVO de la matrícula a buscar.
     * @return Un Optional que puede contener la MatriculaVO si se encuentra.
     * 
     */
	Optional<MatriculaVO> findByUsuarioAndTipocarnet(UsuarioVO usuario, PermisoVO tipocarnet);
	/**
	 * Busca una lista de MatriculaVO por el nombre del carnet.
	 * 
	 * @param nombre El nombre del carnet a buscar.
	 * @return Un Optional que puede contener la lista de MatriculaVO si se encuentra.
	 * 
	 */
	Optional<List<MatriculaVO>> buscarUsuariosCarnet(String nombre);

	/**
	 * Obtiene una lista de PermisoVO por el ID del usuario.
	 * 
	 * @param idusuario El ID del usuario para buscar sus permisos.
	 * @return Una lista de PermisoVO que pertenecen al usuario.
	 * 
	 */
	List<PermisoVO> obtenerPermisosPorUsuarioId(int idusuario);

	/**
	 * Verifica si un usuario está matriculado en un permiso.
	 * 
	 * @param usuario El UsuarioVO a verificar.
	 * @param permiso El PermisoVO a verificar.
	 * @return Verdadero si el usuario está matriculado en el permiso, falso en caso contrario.
	 * 
	 */
	boolean isUsuarioMatriculadoEnPermiso(UsuarioVO usuario, PermisoVO permiso);

	/**
	 * Elimina todas las MatriculaVO por el ID del usuario.
	 * 
	 * @param idusuario El ID del usuario para eliminar sus matrículas.
	 * 
	 */
	void deleteAllByUsuarioId(int idusuario);
    
	<S extends MatriculaVO> S save(S entity);

	<S extends MatriculaVO> List<S> saveAll(Iterable<S> entities);

	<S extends MatriculaVO> Optional<S> findOne(Example<S> example);

	List<MatriculaVO> findAll(Sort sort);

	void flush();

	Page<MatriculaVO> findAll(Pageable pageable);

	<S extends MatriculaVO> S saveAndFlush(S entity);

	<S extends MatriculaVO> List<S> saveAllAndFlush(Iterable<S> entities);

	List<MatriculaVO> findAll();

	List<MatriculaVO> findAllById(Iterable<Integer> ids);

	void deleteInBatch(Iterable<MatriculaVO> entities);

	<S extends MatriculaVO> Page<S> findAll(Example<S> example, Pageable pageable);

	Optional<MatriculaVO> findById(Integer id);

	void deleteAllInBatch(Iterable<MatriculaVO> entities);

	boolean existsById(Integer id);

	<S extends MatriculaVO> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	<S extends MatriculaVO> boolean exists(Example<S> example);

	void deleteAllInBatch();

	MatriculaVO getOne(Integer id);

	<S extends MatriculaVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	long count();

	void deleteById(Integer id);

	MatriculaVO getById(Integer id);

	void delete(MatriculaVO entity);

	void deleteAllById(Iterable<? extends Integer> ids);

	MatriculaVO getReferenceById(Integer id);

	void deleteAll(Iterable<? extends MatriculaVO> entities);

	<S extends MatriculaVO> List<S> findAll(Example<S> example);

	<S extends MatriculaVO> List<S> findAll(Example<S> example, Sort sort);

	void deleteAll();

}