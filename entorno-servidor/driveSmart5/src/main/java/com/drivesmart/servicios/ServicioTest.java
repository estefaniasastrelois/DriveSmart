package com.drivesmart.servicios;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.drivesmart.modelo.TestVO;
import com.drivesmart.modelo.PermisoVO;

/**
 * Interfaz del servicio para la entidad TestVO.
 * Esta interfaz define los métodos que deben ser implementados por el servicio de Test.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface ServicioTest {

    /**
     * Busca un TestVO por su referencia.
     * 
     * @param referencia La referencia del test a buscar.
     * @return Un Optional que puede contener el TestVO si se encuentra.
     * 
     */
    Optional<TestVO> findByReferencia(String referencia);

    /**
     * Busca una lista de TestVO por el nombre del permiso y el nombre del ámbito.
     * 
     * @param nombrePermiso El nombre del permiso para buscar los tests.
     * @param nombreAmbito El nombre del ámbito para buscar los tests.
     * @return Una lista de TestVO que coinciden con los criterios de búsqueda.
     * 
     */
    List<TestVO> findByPermisoNombreAndAmbito(String nombrePermiso, String nombreAmbito);
	
    <S extends TestVO> S save(S entity);

	<S extends TestVO> List<S> saveAll(Iterable<S> entities);

	<S extends TestVO> Optional<S> findOne(Example<S> example);

	List<TestVO> findAll(Sort sort);

	void flush();

	Page<TestVO> findAll(Pageable pageable);

	<S extends TestVO> S saveAndFlush(S entity);

	<S extends TestVO> List<S> saveAllAndFlush(Iterable<S> entities);

	List<TestVO> findAll();

	List<TestVO> findAllById(Iterable<Integer> ids);

	void deleteInBatch(Iterable<TestVO> entities);

	<S extends TestVO> Page<S> findAll(Example<S> example, Pageable pageable);

	Optional<TestVO> findById(Integer id);

	void deleteAllInBatch(Iterable<TestVO> entities);

	boolean existsById(Integer id);

	<S extends TestVO> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	<S extends TestVO> boolean exists(Example<S> example);

	void deleteAllInBatch();

	TestVO getOne(Integer id);

	<S extends TestVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	long count();

	void deleteById(Integer id);

	TestVO getById(Integer id);

	void delete(TestVO entity);

	void deleteAllById(Iterable<? extends Integer> ids);

	TestVO getReferenceById(Integer id);

	void deleteAll(Iterable<? extends TestVO> entities);

	<S extends TestVO> List<S> findAll(Example<S> example);

	<S extends TestVO> List<S> findAll(Example<S> example, Sort sort);

	void deleteAll();

}