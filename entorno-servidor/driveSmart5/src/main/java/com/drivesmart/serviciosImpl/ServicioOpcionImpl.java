package com.drivesmart.serviciosImpl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.drivesmart.modelo.OpcionVO;
import com.drivesmart.modelo.PreguntaVO;
import com.drivesmart.repositorio.OpcionRepository;
import com.drivesmart.servicios.ServicioOpcion;

/**
 * Implementación del servicio para la entidad OpcionVO.
 * Esta clase contiene los métodos implementados por el servicio de Opción.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@Service
public class ServicioOpcionImpl implements ServicioOpcion {
    @Autowired
    private OpcionRepository or;

    /**
     * Busca una lista de OpcionVO por el ID de la pregunta.
     * 
     * @param idpregunta El ID de la pregunta para buscar las opciones.
     * @return Una lista de OpcionVO que coinciden con el criterio de búsqueda.
     * 
     */
    public List<OpcionVO> findByPreguntaIdpregunta(int idpregunta){
        return or.findByPreguntaIdpregunta(idpregunta);
    }
    
    /**
     * Encuentra el ID máximo entre las opciones.
     * 
     * @return El ID máximo encontrado entre las opciones.
     * 
     */
    public Integer findMaxIdOpcion() {
        return or.findMaxIdOpcion();
    }
    
    /**
     * Elimina todas las opciones asociadas a una pregunta específica.
     * 
     * @param idpregunta El ID de la pregunta para la cual se eliminarán todas las opciones.
     * 
     */
    public void eliminarOpcionesDePregunta(int idpregunta) {
        or.deleteAllByIdpregunta(idpregunta);
    }
	
	@Override
	public <S extends OpcionVO> S save(S entity) {
		return or.save(entity);
	}

	@Override
	public <S extends OpcionVO> List<S> saveAll(Iterable<S> entities) {
		return or.saveAll(entities);
	}

	@Override
	public <S extends OpcionVO> Optional<S> findOne(Example<S> example) {
		return or.findOne(example);
	}

	@Override
	public List<OpcionVO> findAll(Sort sort) {
		return or.findAll(sort);
	}

	@Override
	public void flush() {
		or.flush();
	}

	@Override
	public Page<OpcionVO> findAll(Pageable pageable) {
		return or.findAll(pageable);
	}

	@Override
	public <S extends OpcionVO> S saveAndFlush(S entity) {
		return or.saveAndFlush(entity);
	}

	@Override
	public <S extends OpcionVO> List<S> saveAllAndFlush(Iterable<S> entities) {
		return or.saveAllAndFlush(entities);
	}

	@Override
	public List<OpcionVO> findAll() {
		return or.findAll();
	}

	@Override
	public List<OpcionVO> findAllById(Iterable<Integer> ids) {
		return or.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<OpcionVO> entities) {
		or.deleteInBatch(entities);
	}

	@Override
	public <S extends OpcionVO> Page<S> findAll(Example<S> example, Pageable pageable) {
		return or.findAll(example, pageable);
	}

	@Override
	public Optional<OpcionVO> findById(Integer id) {
		return or.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<OpcionVO> entities) {
		or.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return or.existsById(id);
	}

	@Override
	public <S extends OpcionVO> long count(Example<S> example) {
		return or.count(example);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		or.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends OpcionVO> boolean exists(Example<S> example) {
		return or.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		or.deleteAllInBatch();
	}

	@Override
	public OpcionVO getOne(Integer id) {
		return or.getOne(id);
	}

	@Override
	public <S extends OpcionVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return or.findBy(example, queryFunction);
	}

	@Override
	public long count() {
		return or.count();
	}

	@Override
	public void deleteById(Integer id) {
		or.deleteById(id);
	}

	@Override
	public OpcionVO getById(Integer id) {
		return or.getById(id);
	}

	@Override
	public void delete(OpcionVO entity) {
		or.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		or.deleteAllById(ids);
	}

	@Override
	public OpcionVO getReferenceById(Integer id) {
		return or.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends OpcionVO> entities) {
		or.deleteAll(entities);
	}

	@Override
	public <S extends OpcionVO> List<S> findAll(Example<S> example) {
		return or.findAll(example);
	}

	@Override
	public <S extends OpcionVO> List<S> findAll(Example<S> example, Sort sort) {
		return or.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		or.deleteAll();
	}
	
	
}
