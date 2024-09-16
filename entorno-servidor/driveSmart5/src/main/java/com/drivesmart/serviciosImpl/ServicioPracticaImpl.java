package com.drivesmart.serviciosImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.drivesmart.modelo.PracticaVO;
import com.drivesmart.modelo.UsuarioVO;
import com.drivesmart.repositorio.PracticaRepository;
import com.drivesmart.servicios.ServicioPractica;

/**
 * Implementación del servicio para la entidad PracticaVO.
 * Esta clase contiene los métodos implementados por el servicio de Practica.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@Service
public class ServicioPracticaImpl implements ServicioPractica {
    @Autowired
    private PracticaRepository pr;

    /**
     * Busca una lista de PracticaVO por el ID del usuario ordenadas por fecha y hora en orden ascendente.
     * 
     * @param idusuario El ID del usuario para buscar las prácticas.
     * @return Una lista de PracticaVO que coinciden con el criterio de búsqueda.
     * 
     */
    @Override
    public List<PracticaVO> findByUsuarioIdusuarioOrderByFechahoraAsc(int idusuario) {
        return pr.findByUsuarioIdusuarioOrderByFechahoraAsc(idusuario);
    }
    
    /**
     * Busca una lista de PracticaVO por el día especificado.
     * 
     * @param day El día para buscar las prácticas.
     * @return Una lista de PracticaVO que coinciden con el criterio de búsqueda.
     * 
     */
    public List<PracticaVO> findPracticasByDay(LocalDateTime day) {
        LocalDateTime startOfDay = day.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        return pr.findByFechahoraBetweenOrderByFechahoraAsc(startOfDay, endOfDay);
    }
    
    /**
     * Busca una lista de PracticaVO por el ID del permiso ordenadas por fecha y hora en orden ascendente.
     * 
     * @param idpermiso El ID del permiso para buscar las prácticas.
     * @return Una lista de PracticaVO que coinciden con el criterio de búsqueda.
     * 
     */
    public List<PracticaVO> obtenerPracticasPorPermiso(int idpermiso) {
        return pr.findByPermisoIdpermisoOrderByFechahoraAsc(idpermiso);
    }
    
    /**
     * Busca una lista de PracticaVO por el ID del usuario y el ID del permiso ordenadas por fecha y hora en orden ascendente.
     * 
     * @param idusuario El ID del usuario para buscar las prácticas.
     * @param idpermiso El ID del permiso para buscar las prácticas.
     * @return Una lista de PracticaVO que coinciden con el criterio de búsqueda.
     * 
     */
    public List<PracticaVO> getPracticasByUsuarioAndPermiso(int idusuario, int idpermiso) {
        return pr.findByUsuarioIdusuarioAndPermisoIdpermisoOrderByFechahoraAsc(idusuario, idpermiso);
    }
    
    /**
     * Elimina todas las prácticas asociadas a un usuario específico.
     * 
     * @param idusuario El ID del usuario para el cual se eliminarán todas las prácticas.
     * 
     */
    public void eliminarPracticasDeUsuario(int idusuario) {
        pr.deleteAllByIdusuario(idusuario);
    }
	
	@Override
	public <S extends PracticaVO> S save(S entity) {
		PracticaVO practica=new PracticaVO();
		try {
			practica=pr.save(entity);
		}catch(DataAccessException e) {
			if(e.getCause() instanceof DataIntegrityViolationException) {
				System.out.println("Error: No se ha insertado la práctica porque ya existe una programada en la misma fecha y para el mismo usuario");
			}
		}
		return (S) practica;
	}
	@Override
	public <S extends PracticaVO> List<S> saveAll(Iterable<S> entities) {
		return pr.saveAll(entities);
	}

	@Override
	public <S extends PracticaVO> Optional<S> findOne(Example<S> example) {
		return pr.findOne(example);
	}

	@Override
	public List<PracticaVO> findAll(Sort sort) {
		return pr.findAll(sort);
	}

	@Override
	public void flush() {
		pr.flush();
	}

	@Override
	public Page<PracticaVO> findAll(Pageable pageable) {
		return pr.findAll(pageable);
	}

	@Override
	public <S extends PracticaVO> S saveAndFlush(S entity) {
		return pr.saveAndFlush(entity);
	}

	@Override
	public <S extends PracticaVO> List<S> saveAllAndFlush(Iterable<S> entities) {
		return pr.saveAllAndFlush(entities);
	}

	@Override
	public List<PracticaVO> findAll() {
		return pr.findAll();
	}

	@Override
	public List<PracticaVO> findAllById(Iterable<Integer> ids) {
		return pr.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<PracticaVO> entities) {
		pr.deleteInBatch(entities);
	}

	@Override
	public <S extends PracticaVO> Page<S> findAll(Example<S> example, Pageable pageable) {
		return pr.findAll(example, pageable);
	}

	@Override
	public Optional<PracticaVO> findById(Integer id) {
		return pr.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<PracticaVO> entities) {
		pr.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return pr.existsById(id);
	}

	@Override
	public <S extends PracticaVO> long count(Example<S> example) {
		return pr.count(example);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		pr.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends PracticaVO> boolean exists(Example<S> example) {
		return pr.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		pr.deleteAllInBatch();
	}

	@Override
	public PracticaVO getOne(Integer id) {
		return pr.getOne(id);
	}

	@Override
	public <S extends PracticaVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return pr.findBy(example, queryFunction);
	}

	@Override
	public long count() {
		return pr.count();
	}

	@Override
	public void deleteById(Integer id) {
		pr.deleteById(id);
	}

	@Override
	public PracticaVO getById(Integer id) {
		return pr.getById(id);
	}

	@Override
	public void delete(PracticaVO entity) {
		pr.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		pr.deleteAllById(ids);
	}

	@Override
	public PracticaVO getReferenceById(Integer id) {
		return pr.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends PracticaVO> entities) {
		pr.deleteAll(entities);
	}

	@Override
	public <S extends PracticaVO> List<S> findAll(Example<S> example) {
		return pr.findAll(example);
	}

	@Override
	public <S extends PracticaVO> List<S> findAll(Example<S> example, Sort sort) {
		return pr.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		pr.deleteAll();
	}
	
	
}
