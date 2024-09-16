package com.drivesmart.serviciosImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.drivesmart.modelo.RealizaVO;
import com.drivesmart.modelo.UsuarioVO;
import com.drivesmart.repositorio.RealizaRepository;
import com.drivesmart.servicios.ServicioRealiza;

/**
 * Implementación del servicio para la entidad RealizaVO.
 * Esta clase contiene los métodos implementados por el servicio de Realiza.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@Service
public class ServicioRealizaImpl implements ServicioRealiza {
    @Autowired
    private RealizaRepository rr;

    /**
     * Busca una RealizaVO por su usuario y fecha y hora.
     * 
     * @param usuario El usuario de la realización a buscar.
     * @param fechahora La fecha y hora de la realización a buscar.
     * @return Un Optional que puede contener la RealizaVO si se encuentra.
     * 
     */
    @Override
    public Optional<RealizaVO> findByUsuarioAndFechahora(UsuarioVO usuario, LocalDateTime fechahora) {
        return rr.findByUsuarioAndFechahora(usuario, fechahora);
    }
    
    /**
     * Busca una lista de RealizaVO por el ID del usuario.
     * 
     * @param idusuario El ID del usuario para buscar las realizaciones.
     * @return Un Optional que puede contener la lista de RealizaVO si se encuentran.
     * 
     */
    public Optional<List<RealizaVO>> buscarTestAlumno(int idusuario) {
        return rr.buscarTestAlumno(idusuario);
    }
    
    /**
     * Cuenta el número de tests realizados por un usuario específico.
     * 
     * @param idUsuario El ID del usuario para el cual se contarán los tests.
     * @return El número de tests realizados por el usuario.
     * 
     */
    public long recuentoTestByUsuarioId(int idUsuario) {
        return rr.recuentoTestByUsuarioId(idUsuario);
    }
    
    /**
     * Cuenta el número de tests aprobados por un usuario específico.
     * 
     * @param idUsuario El ID del usuario para el cual se contarán los tests aprobados.
     * @return El número de tests aprobados por el usuario.
     * 
     */
    public long recuentoTestAprobadosByUsuarioId(int idUsuario) {
            return rr.recuentoTestAprobadosByUsuarioId(idUsuario);
    }
    
    /**
     * Cuenta el número de tests suspensos por un usuario específico.
     * 
     * @param idUsuario El ID del usuario para el cual se contarán los tests suspensos.
     * @return El número de tests suspensos por el usuario.
     * 
     */
    public long recuentoTestSuspensosByUsuarioId(int idUsuario) {
        return rr.recuentoTestSuspensosByUsuarioId(idUsuario);
    } 
    
    /**
     * Calcula la media de fallos en los tests de un usuario específico.
     * 
     * @param idUsuario El ID del usuario para el cual se calculará la media de fallos.
     * @return La media de fallos en los tests del usuario.
     * 
     */
    public Double mediaFallosByUsuarioId(int idUsuario) {
        return rr.mediaFallosByUsuarioId(idUsuario);
    }

    /**
     * Cuenta el número de tests por tema realizados por un usuario específico.
     * 
     * @param idUsuario El ID del usuario para el cual se contarán los tests por tema.
     * @return El número de tests por tema realizados por el usuario.
     * 
     */
    public long countTestByTema(int idUsuario) {
        return rr.countTestByTema(idUsuario);
    }
    
    /**
     * Cuenta el número de tests globales realizados por un usuario específico.
     * 
     * @param idUsuario El ID del usuario para el cual se contarán los tests globales.
     * @return El número de tests globales realizados por el usuario.
     * 
     */
    public long countTestGlobales(int idUsuario) {
        return rr.countTestGlobales(idUsuario);
    }
    
    /**
     * Calcula el porcentaje de tests aprobados por un usuario específico.
     * 
     * @param idUsuario El ID del usuario para el cual se calculará el porcentaje de tests aprobados.
     * @return El porcentaje de tests aprobados por el usuario.
     * 
     */
    public Double porcentajeTestAprobados(int idUsuario) {
        return rr.porcentajeTestAprobados(idUsuario);
    }
    
    /**
     * Calcula el porcentaje de tests suspensos por un usuario específico.
     * 
     * @param idUsuario El ID del usuario para el cual se calculará el porcentaje de tests suspensos.
     * @return El porcentaje de tests suspensos por el usuario.
     * 
     */
    public Double porcentajeTestSuspensos(int idUsuario) {
        return rr.porcentajeTestSuspensos(idUsuario);
    }
    
    /**
     * Encuentra el tema más suspendido por un usuario específico.
     * 
     * @param idUsuario El ID del usuario para el cual se buscará el tema más suspendido.
     * @return Un Optional que puede contener el tema más suspendido si se encuentra.
     * 
     */
    public Optional<String> temaMasSuspendido(int idUsuario) {
        return rr.temaMasSuspendido(idUsuario);
    }
    
    /**
     * Encuentra el tema más aprobado por un usuario específico.
     * 
     * @param idUsuario El ID del usuario para el cual se buscará el tema más aprobado.
     * @return Un Optional que puede contener el tema más aprobado si se encuentra.
     * 
     */
    public Optional<String> temaMasAprobado(int idUsuario) {
        return rr.temaMasAprobado(idUsuario);
    }
    
    /**
     * Cuenta el número de tests aprobados este mes por un usuario específico.
     * 
     * @param idUsuario El ID del usuario para el cual se contarán los tests aprobados este mes.
     * @return El número de tests aprobados este mes por el usuario.
     * 
     */
    public long countAprobadosEsteMes(int idUsuario) {
        return rr.countAprobadosEsteMes(idUsuario);
    }

    /**
     * Cuenta el número de tests aprobados el mes pasado por un usuario específico.
     * 
     * @param idUsuario El ID del usuario para el cual se contarán los tests aprobados el mes pasado.
     * @return El número de tests aprobados el mes pasado por el usuario.
     * 
     */
    public long countAprobadosMesPasado(int idUsuario) {
        return rr.countAprobadosMesPasado(idUsuario);
    }

    /**
     * Comprueba si el usuario ha mejorado este mes en comparación con el mes pasado.
     * 
     * @param idUsuario El ID del usuario para el cual se comprobará la progresión.
     * @return Verdadero si el usuario ha aprobado más tests este mes que el mes pasado, falso en caso contrario.
     * 
     */
    public boolean progresionMesActual(int idUsuario) {
        long aprobadosEsteMes = countAprobadosEsteMes(idUsuario);
        long aprobadosMesPasado = countAprobadosMesPasado(idUsuario);
        return aprobadosEsteMes > aprobadosMesPasado;
    }

    /**
     * Encuentra el tema menos practicado por un usuario específico.
     * 
     * @param idUsuario El ID del usuario para el cual se buscará el tema menos practicado.
     * @return Un Optional que puede contener el tema menos practicado si se encuentra.
     * 
     */
    public Optional<String> temaMenosPracticado(int idUsuario) {
        List<Object[]> result = rr.countTestsByTemaForUser(idUsuario);
        if (!result.isEmpty()) {
            return Optional.of((String) result.get(0)[0]);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Calcula el porcentaje de tests por tema para un usuario específico.
     * 
     * @param idUsuario El ID del usuario para el cual se calcularán los porcentajes.
     * @return Un mapa con los temas como claves y los porcentajes como valores.
     * 
     */
    public Map<String, Double> porcentajeTestsPorTema(int idUsuario) {
        List<Object[]> counts = rr.countTestsByTema(idUsuario);
        long totalTests = recuentoTestByUsuarioId(idUsuario);

        Map<String, Double> porcentajes = new HashMap<>();
        for (Object[] count : counts) {
            String tema = (String) count[0];
            long countByTema = ((Number) count[1]).longValue();
            double porcentaje = totalTests > 0 ? (double) countByTema / totalTests * 100 : 0.0;
            porcentajes.put(tema, porcentaje);
        }
        return porcentajes;
    }

    /**
     * Elimina todas las realizaciones de un usuario específico.
     * 
     * @param idusuario El ID del usuario para el cual se eliminarán todas las realizaciones.
     * 
     */
    public void eliminarRealizaDeUsuario(int idusuario) {
        rr.deleteAllByIdusuario(idusuario);
    }

    /**
     * Elimina todas las realizaciones de un test específico.
     * 
     * @param idtest El ID del test para el cual se eliminarán todas las realizaciones.
     * 
     */
    public void eliminarRealizaDeTest(int idtest) {
        rr.deleteAllByIdtest(idtest);
    }
    
    
	@Override
	public <S extends RealizaVO> S save(S entity) {
		return rr.save(entity);
	}

	@Override
	public <S extends RealizaVO> List<S> saveAll(Iterable<S> entities) {
		return rr.saveAll(entities);
	}

	@Override
	public <S extends RealizaVO> Optional<S> findOne(Example<S> example) {
		return rr.findOne(example);
	}

	@Override
	public List<RealizaVO> findAll(Sort sort) {
		return rr.findAll(sort);
	}

	@Override
	public void flush() {
		rr.flush();
	}

	@Override
	public Page<RealizaVO> findAll(Pageable pageable) {
		return rr.findAll(pageable);
	}

	@Override
	public <S extends RealizaVO> S saveAndFlush(S entity) {
		return rr.saveAndFlush(entity);
	}

	@Override
	public <S extends RealizaVO> List<S> saveAllAndFlush(Iterable<S> entities) {
		return rr.saveAllAndFlush(entities);
	}

	@Override
	public List<RealizaVO> findAll() {
		return rr.findAll();
	}

	@Override
	public List<RealizaVO> findAllById(Iterable<Integer> ids) {
		return rr.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<RealizaVO> entities) {
		rr.deleteInBatch(entities);
	}

	@Override
	public <S extends RealizaVO> Page<S> findAll(Example<S> example, Pageable pageable) {
		return rr.findAll(example, pageable);
	}

	@Override
	public Optional<RealizaVO> findById(Integer id) {
		return rr.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<RealizaVO> entities) {
		rr.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return rr.existsById(id);
	}

	@Override
	public <S extends RealizaVO> long count(Example<S> example) {
		return rr.count(example);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		rr.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends RealizaVO> boolean exists(Example<S> example) {
		return rr.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		rr.deleteAllInBatch();
	}

	@Override
	public RealizaVO getOne(Integer id) {
		return rr.getOne(id);
	}

	@Override
	public <S extends RealizaVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return rr.findBy(example, queryFunction);
	}

	@Override
	public long count() {
		return rr.count();
	}

	@Override
	public void deleteById(Integer id) {
		rr.deleteById(id);
	}

	@Override
	public RealizaVO getById(Integer id) {
		return rr.getById(id);
	}

	@Override
	public void delete(RealizaVO entity) {
		rr.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		rr.deleteAllById(ids);
	}

	@Override
	public RealizaVO getReferenceById(Integer id) {
		return rr.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends RealizaVO> entities) {
		rr.deleteAll(entities);
	}

	@Override
	public <S extends RealizaVO> List<S> findAll(Example<S> example) {
		return rr.findAll(example);
	}

	@Override
	public <S extends RealizaVO> List<S> findAll(Example<S> example, Sort sort) {
		return rr.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		rr.deleteAll();
	}
	
	
}
