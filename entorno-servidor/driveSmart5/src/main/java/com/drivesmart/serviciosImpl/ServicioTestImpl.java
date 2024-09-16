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

import com.drivesmart.modelo.TestVO;
import com.drivesmart.modelo.PermisoVO;
import com.drivesmart.modelo.UsuarioVO;
import com.drivesmart.repositorio.TestRepository;
import com.drivesmart.servicios.ServicioTest;

/**
 * Implementación del servicio para la entidad TestVO.
 * Esta clase contiene los métodos implementados por el servicio de Test.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 3.0
 */
@Service
public class ServicioTestImpl implements ServicioTest {
	@Autowired
	private TestRepository tr;

	/**
     * Busca un test por su referencia.
     * 
     * @param referencia La referencia del test que se buscará.
     * @return Un Optional que puede contener el TestVO si se encuentra.
     * 
     */
    @Override
    public Optional<TestVO> findByReferencia(String referencia) {
        return tr.findByReferencia(referencia);
    }

    /**
     * Busca tests por el nombre del permiso y el ámbito.
     * 
     * @param nombrePermiso El nombre del permiso que se buscará.
     * @param nombreAmbito El nombre del ámbito que se buscará.
     * @return Una lista de TestVO que coinciden con los criterios de búsqueda.
     * 
     */
    public List<TestVO> findByPermisoNombreAndAmbito(String nombrePermiso, String nombreAmbito){
        return tr.findByPermisoNombreAndAmbito(nombrePermiso, nombreAmbito);
    }
	
	@Override
	public <S extends TestVO> S save(S entity) {
		TestVO test=new TestVO();
		try {
			test=tr.save(entity);
		}catch(DataAccessException e) {
			if(e.getCause() instanceof DataIntegrityViolationException) {
				System.out.println("Error: No se ha insertado el test porque ya existe un test con esa referencia");
			}
		}
		return (S) test;
	}

	@Override
	public <S extends TestVO> List<S> saveAll(Iterable<S> entities) {
		return tr.saveAll(entities);
	}

	@Override
	public <S extends TestVO> Optional<S> findOne(Example<S> example) {
		return tr.findOne(example);
	}

	@Override
	public List<TestVO> findAll(Sort sort) {
		return tr.findAll(sort);
	}

	@Override
	public void flush() {
		tr.flush();
	}

	@Override
	public Page<TestVO> findAll(Pageable pageable) {
		return tr.findAll(pageable);
	}

	@Override
	public <S extends TestVO> S saveAndFlush(S entity) {
		return tr.saveAndFlush(entity);
	}

	@Override
	public <S extends TestVO> List<S> saveAllAndFlush(Iterable<S> entities) {
		return tr.saveAllAndFlush(entities);
	}

	@Override
	public List<TestVO> findAll() {
		return tr.findAll();
	}

	@Override
	public List<TestVO> findAllById(Iterable<Integer> ids) {
		return tr.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<TestVO> entities) {
		tr.deleteInBatch(entities);
	}

	@Override
	public <S extends TestVO> Page<S> findAll(Example<S> example, Pageable pageable) {
		return tr.findAll(example, pageable);
	}

	@Override
	public Optional<TestVO> findById(Integer id) {
		return tr.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<TestVO> entities) {
		tr.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return tr.existsById(id);
	}

	@Override
	public <S extends TestVO> long count(Example<S> example) {
		return tr.count(example);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		tr.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends TestVO> boolean exists(Example<S> example) {
		return tr.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		tr.deleteAllInBatch();
	}

	@Override
	public TestVO getOne(Integer id) {
		return tr.getOne(id);
	}

	@Override
	public <S extends TestVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
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
	public TestVO getById(Integer id) {
		return tr.getById(id);
	}

	@Override
	public void delete(TestVO entity) {
		tr.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		tr.deleteAllById(ids);
	}

	@Override
	public TestVO getReferenceById(Integer id) {
		return tr.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends TestVO> entities) {
		tr.deleteAll(entities);
	}

	@Override
	public <S extends TestVO> List<S> findAll(Example<S> example) {
		return tr.findAll(example);
	}

	@Override
	public <S extends TestVO> List<S> findAll(Example<S> example, Sort sort) {
		return tr.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		tr.deleteAll();
	}
	
	
}
