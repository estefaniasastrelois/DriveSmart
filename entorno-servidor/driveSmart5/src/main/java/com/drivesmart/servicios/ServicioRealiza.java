package com.drivesmart.servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.drivesmart.modelo.RealizaVO;
import com.drivesmart.modelo.UsuarioVO;

/**
 * Interfaz del servicio para la entidad RealizaVO.
 * Esta interfaz define los métodos que deben ser implementados por el servicio de Realiza.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface ServicioRealiza {

    /**
     * Busca un RealizaVO por su UsuarioVO y fecha y hora.
     * 
     * @param usuario El UsuarioVO de la realización a buscar.
     * @param fechahora La fecha y hora de la realización a buscar.
     * @return Un Optional que puede contener el RealizaVO si se encuentra.
     * 
     */
    Optional<RealizaVO> findByUsuarioAndFechahora(UsuarioVO usuario, LocalDateTime fechahora);

    /**
     * Busca una lista de RealizaVO por el ID del usuario.
     * 
     * @param idusuario El ID del usuario para buscar sus realizaciones.
     * @return Un Optional que puede contener una lista de RealizaVO si se encuentran.
     * 
     */
    Optional<List<RealizaVO>> buscarTestAlumno(int idusuario);

    /**
     * Cuenta el número de tests realizados por un usuario.
     * 
     * @param idUsuario El ID del usuario para contar sus tests.
     * @return El número de tests realizados por el usuario.
     * 
     */
    long recuentoTestByUsuarioId(int idUsuario);
    /**
     * Cuenta el número de tests aprobados por un usuario.
     * 
     * @param idUsuario El ID del usuario para contar sus tests aprobados.
     * @return El número de tests aprobados por el usuario.
     * 
     */
    long recuentoTestAprobadosByUsuarioId(int idUsuario);

    /**
     * Cuenta el número de tests suspensos por un usuario.
     * 
     * @param idUsuario El ID del usuario para contar sus tests suspensos.
     * @return El número de tests suspensos por el usuario.
     * 
     */
    long recuentoTestSuspensosByUsuarioId(int idUsuario);

    /**
     * Calcula la media de fallos por test de un usuario.
     * 
     * @param idUsuario El ID del usuario para calcular su media de fallos.
     * @return La media de fallos por test del usuario.
     * 
     */
    Double mediaFallosByUsuarioId(int idUsuario);
    /**
     * Cuenta el número de tests realizados por un usuario para un tema específico.
     * 
     * @param idUsuario El ID del usuario para contar sus tests.
     * @return El número de tests realizados por el usuario para el tema.
     * 
     */
    long countTestByTema(int idUsuario);

    /**
     * Cuenta el número de tests globales realizados por un usuario.
     * 
     * @param idUsuario El ID del usuario para contar sus tests globales.
     * @return El número de tests globales realizados por el usuario.
     * 
     */
    long countTestGlobales(int idUsuario);

    /**
     * Calcula el porcentaje de tests aprobados por un usuario.
     * 
     * @param idUsuario El ID del usuario para calcular su porcentaje de aprobados.
     * @return El porcentaje de tests aprobados por el usuario.
     * 
     */
    Double porcentajeTestAprobados(int idUsuario);

    /**
     * Calcula el porcentaje de tests suspensos por un usuario.
     * 
     * @param idUsuario El ID del usuario para calcular su porcentaje de suspensos.
     * @return El porcentaje de tests suspensos por el usuario.
     * 
     */
    Double porcentajeTestSuspensos(int idUsuario);

    /**
     * Encuentra el tema en el que un usuario ha suspendido más tests.
     * 
     * @param idUsuario El ID del usuario para encontrar su tema más suspendido.
     * @return Un Optional que puede contener el tema más suspendido por el usuario.
     * 
     */
    Optional<String> temaMasSuspendido(int idUsuario);

    /**
     * Encuentra el tema en el que un usuario ha aprobado más tests.
     * 
     * @param idUsuario El ID del usuario para encontrar su tema más aprobado.
     * @return Un Optional que puede contener el tema más aprobado por el usuario.
     * 
     */
    Optional<String> temaMasAprobado(int idUsuario);
    /**
     * Verifica si un usuario ha progresado en el mes actual.
     * 
     * @param idUsuario El ID del usuario para verificar su progreso.
     * @return Verdadero si el usuario ha progresado en el mes actual, falso en caso contrario.
     * 
     */
    boolean progresionMesActual(int idUsuario);

    /**
     * Encuentra el tema que un usuario ha practicado menos.
     * 
     * @param idUsuario El ID del usuario para encontrar su tema menos practicado.
     * @return Un Optional que puede contener el tema menos practicado por el usuario.
     * 
     */
    Optional<String> temaMenosPracticado(int idUsuario);

    /**
     * Elimina todas las RealizaVO de un usuario.
     * 
     * @param idUsuario El ID del usuario para eliminar sus realizaciones.
     * 
     */
    void eliminarRealizaDeUsuario(int idUsuario);

    /**
     * Elimina todas las RealizaVO de un test.
     * 
     * @param idtest El ID del test para eliminar sus realizaciones.
     * 
     */
    void eliminarRealizaDeTest(int idtest);
    
    Map<String, Double> porcentajeTestsPorTema(int idUsuario);
    
    <S extends RealizaVO> S save(S entity);

	<S extends RealizaVO> List<S> saveAll(Iterable<S> entities);

	<S extends RealizaVO> Optional<S> findOne(Example<S> example);

	List<RealizaVO> findAll(Sort sort);

	void flush();

	Page<RealizaVO> findAll(Pageable pageable);

	<S extends RealizaVO> S saveAndFlush(S entity);

	<S extends RealizaVO> List<S> saveAllAndFlush(Iterable<S> entities);

	List<RealizaVO> findAll();

	List<RealizaVO> findAllById(Iterable<Integer> ids);

	void deleteInBatch(Iterable<RealizaVO> entities);

	<S extends RealizaVO> Page<S> findAll(Example<S> example, Pageable pageable);

	Optional<RealizaVO> findById(Integer id);

	void deleteAllInBatch(Iterable<RealizaVO> entities);

	boolean existsById(Integer id);

	<S extends RealizaVO> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	<S extends RealizaVO> boolean exists(Example<S> example);

	void deleteAllInBatch();

	RealizaVO getOne(Integer id);

	<S extends RealizaVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	long count();

	void deleteById(Integer id);

	RealizaVO getById(Integer id);

	void delete(RealizaVO entity);

	void deleteAllById(Iterable<? extends Integer> ids);

	RealizaVO getReferenceById(Integer id);

	void deleteAll(Iterable<? extends RealizaVO> entities);

	<S extends RealizaVO> List<S> findAll(Example<S> example);

	<S extends RealizaVO> List<S> findAll(Example<S> example, Sort sort);

	void deleteAll();

}
