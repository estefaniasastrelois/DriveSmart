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
import com.drivesmart.repositorio.UsuarioRepository;
import com.drivesmart.servicios.ServicioUsuario;

/**
 * Implementación del servicio para la entidad UsuarioVO.
 * Esta clase contiene los métodos implementados por el servicio de Usuario.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 3.0
 */
@Service
public class ServicioUsuarioImpl implements ServicioUsuario {
	@Autowired
	private UsuarioRepository ur;

	/**
     * Busca un usuario por su DNI.
     * 
     * @param dni El DNI del usuario que se buscará.
     * @return Un Optional que puede contener el UsuarioVO si se encuentra.
     * 
     */
    @Override
    public Optional<UsuarioVO> findByDni(String dni) {
        return ur.findByDni(dni);
    }

    /**
     * Busca un usuario por su correo electrónico.
     * 
     * @param email El correo electrónico del usuario que se buscará.
     * @return Un Optional que puede contener el UsuarioVO si se encuentra.
     * 
     */
    @Override
    public Optional<UsuarioVO> findByEmail(String email) {
        return ur.findByEmail(email);
    }
	
	@Override
	public <S extends UsuarioVO> S save(S entity) {
		UsuarioVO usuario=new UsuarioVO();
		try {
			usuario=ur.save(entity);
		}catch(DataAccessException e) {
			if(e.getCause() instanceof DataIntegrityViolationException) {
				System.out.println("Error: No se ha insertado el usuario porque ese dni ya existe en la base de datos");
			}
		}
		return (S) usuario;
	}

	@Override
	public <S extends UsuarioVO> List<S> saveAll(Iterable<S> entities) {
		return ur.saveAll(entities);
	}

	@Override
	public <S extends UsuarioVO> Optional<S> findOne(Example<S> example) {
		return ur.findOne(example);
	}

	@Override
	public List<UsuarioVO> findAll(Sort sort) {
		return ur.findAll(sort);
	}

	@Override
	public void flush() {
		ur.flush();
	}

	@Override
	public Page<UsuarioVO> findAll(Pageable pageable) {
		return ur.findAll(pageable);
	}

	@Override
	public <S extends UsuarioVO> S saveAndFlush(S entity) {
		return ur.saveAndFlush(entity);
	}

	@Override
	public <S extends UsuarioVO> List<S> saveAllAndFlush(Iterable<S> entities) {
		return ur.saveAllAndFlush(entities);
	}

	@Override
	public List<UsuarioVO> findAll() {
		return ur.findAll();
	}

	@Override
	public List<UsuarioVO> findAllById(Iterable<Integer> ids) {
		return ur.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<UsuarioVO> entities) {
		ur.deleteInBatch(entities);
	}

	@Override
	public <S extends UsuarioVO> Page<S> findAll(Example<S> example, Pageable pageable) {
		return ur.findAll(example, pageable);
	}

	@Override
	public Optional<UsuarioVO> findById(Integer id) {
		return ur.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<UsuarioVO> entities) {
		ur.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return ur.existsById(id);
	}

	@Override
	public <S extends UsuarioVO> long count(Example<S> example) {
		return ur.count(example);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		ur.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends UsuarioVO> boolean exists(Example<S> example) {
		return ur.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		ur.deleteAllInBatch();
	}

	@Override
	public UsuarioVO getOne(Integer id) {
		return ur.getOne(id);
	}

	@Override
	public <S extends UsuarioVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return ur.findBy(example, queryFunction);
	}

	@Override
	public long count() {
		return ur.count();
	}

	@Override
	public void deleteById(Integer id) {
		ur.deleteById(id);
	}

	@Override
	public UsuarioVO getById(Integer id) {
		return ur.getById(id);
	}

	@Override
	public void delete(UsuarioVO entity) {
		ur.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		ur.deleteAllById(ids);
	}

	@Override
	public UsuarioVO getReferenceById(Integer id) {
		return ur.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends UsuarioVO> entities) {
		ur.deleteAll(entities);
	}

	@Override
	public <S extends UsuarioVO> List<S> findAll(Example<S> example) {
		return ur.findAll(example);
	}

	@Override
	public <S extends UsuarioVO> List<S> findAll(Example<S> example, Sort sort) {
		return ur.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		ur.deleteAll();
	}

	
	
}
