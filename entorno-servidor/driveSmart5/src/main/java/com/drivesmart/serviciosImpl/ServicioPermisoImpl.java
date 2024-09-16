package com.drivesmart.serviciosImpl;

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

import com.drivesmart.modelo.PermisoVO;
import com.drivesmart.modelo.UsuarioVO;
import com.drivesmart.repositorio.PermisoRepository;
import com.drivesmart.servicios.ServicioPermiso;

/**
 * Implementación del servicio para la entidad PermisoVO.
 * Esta clase contiene los métodos implementados por el servicio de Permiso.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@Service
public class ServicioPermisoImpl implements ServicioPermiso {
	@Autowired
	private PermisoRepository tcr;

	/**
     * Busca un PermisoVO por su nombre.
     * 
     * @param nombre El nombre del permiso a buscar.
     * @return Un Optional que puede contener el PermisoVO si se encuentra.
     * 
     */
	@Override
	public Optional<PermisoVO> findByNombre(String nombre) {
		return tcr.findByNombre(nombre);
	}

	@Override
	public <S extends PermisoVO> S save(S entity) {
		PermisoVO tipocarnet=new PermisoVO();
		try {
			tipocarnet=tcr.save(entity);
		}catch(DataAccessException e) {
			if(e.getCause() instanceof DataIntegrityViolationException) {
				System.out.println("Error: No se ha insertado el tipo de carnet porque ya existe otro tipo de carnet con el mismo nombre");
			}
		}
		return (S) tipocarnet;
	}

	@Override
	public <S extends PermisoVO> List<S> saveAll(Iterable<S> entities) {
		return tcr.saveAll(entities);
	}

	@Override
	public <S extends PermisoVO> Optional<S> findOne(Example<S> example) {
		return tcr.findOne(example);
	}

	@Override
	public List<PermisoVO> findAll(Sort sort) {
		return tcr.findAll(sort);
	}

	@Override
	public void flush() {
		tcr.flush();
	}

	@Override
	public Page<PermisoVO> findAll(Pageable pageable) {
		return tcr.findAll(pageable);
	}

	@Override
	public <S extends PermisoVO> S saveAndFlush(S entity) {
		return tcr.saveAndFlush(entity);
	}

	@Override
	public <S extends PermisoVO> List<S> saveAllAndFlush(Iterable<S> entities) {
		return tcr.saveAllAndFlush(entities);
	}

	@Override
	public List<PermisoVO> findAll() {
		return tcr.findAll();
	}

	@Override
	public List<PermisoVO> findAllById(Iterable<Integer> ids) {
		return tcr.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<PermisoVO> entities) {
		tcr.deleteInBatch(entities);
	}

	@Override
	public <S extends PermisoVO> Page<S> findAll(Example<S> example, Pageable pageable) {
		return tcr.findAll(example, pageable);
	}

	@Override
	public Optional<PermisoVO> findById(Integer id) {
		return tcr.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<PermisoVO> entities) {
		tcr.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return tcr.existsById(id);
	}

	@Override
	public <S extends PermisoVO> long count(Example<S> example) {
		return tcr.count(example);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		tcr.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends PermisoVO> boolean exists(Example<S> example) {
		return tcr.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		tcr.deleteAllInBatch();
	}

	@Override
	public PermisoVO getOne(Integer id) {
		return tcr.getOne(id);
	}

	@Override
	public <S extends PermisoVO, R> R findBy(Example<S> example,
			Function<FetchableFluentQuery<S>, R> queryFunction) {
		return tcr.findBy(example, queryFunction);
	}

	@Override
	public long count() {
		return tcr.count();
	}

	@Override
	public void deleteById(Integer id) {
		tcr.deleteById(id);
	}

	@Override
	public PermisoVO getById(Integer id) {
		return tcr.getById(id);
	}

	@Override
	public void delete(PermisoVO entity) {
		tcr.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		tcr.deleteAllById(ids);
	}

	@Override
	public PermisoVO getReferenceById(Integer id) {
		return tcr.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends PermisoVO> entities) {
		tcr.deleteAll(entities);
	}

	@Override
	public <S extends PermisoVO> List<S> findAll(Example<S> example) {
		return tcr.findAll(example);
	}

	@Override
	public <S extends PermisoVO> List<S> findAll(Example<S> example, Sort sort) {
		return tcr.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		tcr.deleteAll();
	}
	
	
}
