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

import com.drivesmart.modelo.PreguntaVO;
import com.drivesmart.modelo.TestVO;
import com.drivesmart.repositorio.PreguntaRepository;
import com.drivesmart.servicios.ServicioPregunta;

/**
 * Implementación del servicio para la entidad PreguntaVO.
 * Esta clase contiene los métodos implementados por el servicio de Pregunta.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@Service
public class ServicioPreguntaImpl implements ServicioPregunta {
    @Autowired
    private PreguntaRepository pr;

    /**
     * Busca una PreguntaVO por su enunciado y test.
     * 
     * @param enunciado El enunciado de la pregunta a buscar.
     * @param test El test asociado a la pregunta a buscar.
     * @return Un Optional que puede contener la PreguntaVO si se encuentra.
     * 
     */
    @Override
    public Optional<PreguntaVO> findByEnunciadoAndTest(String enunciado, TestVO test) {
        return pr.findByEnunciadoAndTest(enunciado, test);
    }
    
    /**
     * Busca una lista de PreguntaVO por el ID del test.
     * 
     * @param idtest El ID del test para buscar las preguntas.
     * @return Una lista de PreguntaVO que coinciden con el criterio de búsqueda.
     * 
     */
    public List<PreguntaVO> findByTestIdtest(int idtest) {
        return pr.findByTestIdtest(idtest);
    }
    
    /**
     * Encuentra el ID máximo entre todas las PreguntaVO.
     * 
     * @return El ID máximo encontrado.
     * 
     */
    public Integer findMaxIdPregunta() {
        return pr.findMaxIdPregunta();
    }
    
    /**
     * Elimina todas las preguntas asociadas a un test específico.
     * 
     * @param idtest El ID del test para el cual se eliminarán todas las preguntas.
     * 
     */
    public void eliminarPreguntasDeTest(int idtest) {
        pr.deleteAllByIdtest(idtest);
    }
	
	@Override
	public <S extends PreguntaVO> S save(S entity) {
		PreguntaVO pregunta=new PreguntaVO();
		try {
			pregunta=pr.save(entity);
		}catch(DataAccessException e) {
			if(e.getCause() instanceof DataIntegrityViolationException) {
				System.out.println("Error: No se ha insertado la pregunta porque ya existe otra pregunta en el mismo test con el mismo enunciado");
			}
		}
		return (S) pregunta;
	}

	@Override
	public <S extends PreguntaVO> List<S> saveAll(Iterable<S> entities) {
		return pr.saveAll(entities);
	}

	@Override
	public <S extends PreguntaVO> Optional<S> findOne(Example<S> example) {
		return pr.findOne(example);
	}

	@Override
	public List<PreguntaVO> findAll(Sort sort) {
		return pr.findAll(sort);
	}

	@Override
	public void flush() {
		pr.flush();
	}

	@Override
	public Page<PreguntaVO> findAll(Pageable pageable) {
		return pr.findAll(pageable);
	}

	@Override
	public <S extends PreguntaVO> S saveAndFlush(S entity) {
		return pr.saveAndFlush(entity);
	}

	@Override
	public <S extends PreguntaVO> List<S> saveAllAndFlush(Iterable<S> entities) {
		return pr.saveAllAndFlush(entities);
	}

	@Override
	public List<PreguntaVO> findAll() {
		return pr.findAll();
	}

	@Override
	public List<PreguntaVO> findAllById(Iterable<Integer> ids) {
		return pr.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<PreguntaVO> entities) {
		pr.deleteInBatch(entities);
	}

	@Override
	public <S extends PreguntaVO> Page<S> findAll(Example<S> example, Pageable pageable) {
		return pr.findAll(example, pageable);
	}

	@Override
	public Optional<PreguntaVO> findById(Integer id) {
		return pr.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<PreguntaVO> entities) {
		pr.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return pr.existsById(id);
	}

	@Override
	public <S extends PreguntaVO> long count(Example<S> example) {
		return pr.count(example);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		pr.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends PreguntaVO> boolean exists(Example<S> example) {
		return pr.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		pr.deleteAllInBatch();
	}

	@Override
	public PreguntaVO getOne(Integer id) {
		return pr.getOne(id);
	}

	@Override
	public <S extends PreguntaVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
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
	public PreguntaVO getById(Integer id) {
		return pr.getById(id);
	}

	@Override
	public void delete(PreguntaVO entity) {
		pr.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		pr.deleteAllById(ids);
	}

	@Override
	public PreguntaVO getReferenceById(Integer id) {
		return pr.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends PreguntaVO> entities) {
		pr.deleteAll(entities);
	}

	@Override
	public <S extends PreguntaVO> List<S> findAll(Example<S> example) {
		return pr.findAll(example);
	}

	@Override
	public <S extends PreguntaVO> List<S> findAll(Example<S> example, Sort sort) {
		return pr.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		pr.deleteAll();
	}


	
	
}
