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

import com.drivesmart.modelo.TemaVO;
import com.drivesmart.repositorio.TemaRepository;
import com.drivesmart.servicios.ServicioTema;

/**
 * Implementación del servicio para la entidad TemaVO.
 * Esta clase contiene los métodos implementados por el servicio de Tema.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 3.0
 */
@Service
public class ServicioTemaImpl implements ServicioTema {
	@Autowired
	private TemaRepository tr;

	/**
     * Busca un tema por su nombre.
     * 
     * @param nombre El nombre del tema que se buscará.
     * @return Un Optional que puede contener el TemaVO si se encuentra.
     * 
     */
	@Override
	public Optional<TemaVO> findByNombre(String nombre) {
		return tr.findByNombre(nombre);
	}

	@Override
	public <S extends TemaVO> S save(S entity) {
		return tr.save(entity);
	}

	@Override
	public <S extends TemaVO> List<S> saveAll(Iterable<S> entities) {
		return tr.saveAll(entities);
	}

	@Override
	public <S extends TemaVO> Optional<S> findOne(Example<S> example) {
		return tr.findOne(example);
	}

	@Override
	public List<TemaVO> findAll(Sort sort) {
		return tr.findAll(sort);
	}

	@Override
	public void flush() {
		tr.flush();
	}

	@Override
	public Page<TemaVO> findAll(Pageable pageable) {
		return tr.findAll(pageable);
	}

	@Override
	public <S extends TemaVO> S saveAndFlush(S entity) {
		return tr.saveAndFlush(entity);
	}

	@Override
	public <S extends TemaVO> List<S> saveAllAndFlush(Iterable<S> entities) {
		return tr.saveAllAndFlush(entities);
	}

	@Override
	public List<TemaVO> findAll() {
		return tr.findAll();
	}

	@Override
	public List<TemaVO> findAllById(Iterable<Integer> ids) {
		return tr.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<TemaVO> entities) {
		tr.deleteInBatch(entities);
	}

	@Override
	public <S extends TemaVO> Page<S> findAll(Example<S> example, Pageable pageable) {
		return tr.findAll(example, pageable);
	}

	@Override
	public Optional<TemaVO> findById(Integer id) {
		return tr.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<TemaVO> entities) {
		tr.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return tr.existsById(id);
	}

	@Override
	public <S extends TemaVO> long count(Example<S> example) {
		return tr.count(example);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		tr.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends TemaVO> boolean exists(Example<S> example) {
		return tr.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		tr.deleteAllInBatch();
	}

	@Override
	public TemaVO getOne(Integer id) {
		return tr.getOne(id);
	}

	@Override
	public <S extends TemaVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return tr.findBy(example, queryFunction);
	}

	@Override
	public long count() {
		return tr.count();
	}

	@Override
	public void deleteById(Integer id) {
		tr.deleteById(id);
	}

	@Override
	public TemaVO getById(Integer id) {
		return tr.getById(id);
	}

	@Override
	public void delete(TemaVO entity) {
		tr.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		tr.deleteAllById(ids);
	}

	@Override
	public TemaVO getReferenceById(Integer id) {
		return tr.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends TemaVO> entities) {
		tr.deleteAll(entities);
	}

	@Override
	public <S extends TemaVO> List<S> findAll(Example<S> example) {
		return tr.findAll(example);
	}

	@Override
	public <S extends TemaVO> List<S> findAll(Example<S> example, Sort sort) {
		return tr.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		tr.deleteAll();
	}
	
	
}
