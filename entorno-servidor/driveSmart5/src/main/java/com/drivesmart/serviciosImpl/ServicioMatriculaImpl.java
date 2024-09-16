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

import com.drivesmart.modelo.MatriculaVO;
import com.drivesmart.modelo.PermisoVO;
import com.drivesmart.modelo.UsuarioVO;
import com.drivesmart.repositorio.MatriculaRepository;
import com.drivesmart.servicios.ServicioMatricula;

/**
 * Implementación del servicio para la entidad MatriculaVO.
 * Esta clase contiene los métodos implementados por el servicio de Matricula.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@Service
public class ServicioMatriculaImpl implements ServicioMatricula {
    @Autowired
    private MatriculaRepository mr;

    /**
     * Busca una MatriculaVO por su usuario y tipo de carnet.
     * 
     * @param usuario El usuario de la matrícula a buscar.
     * @param permiso El permiso de la matrícula a buscar.
     * @return Un Optional que puede contener la MatriculaVO si se encuentra.
     * 
     */
    @Override
    public Optional<MatriculaVO> findByUsuarioAndTipocarnet(UsuarioVO usuario, PermisoVO permiso) {
        return mr.findByUsuarioAndPermiso(usuario, permiso);
    }
    
    /**
     * Busca una lista de MatriculaVO por el nombre del permiso.
     * 
     * @param nombre El nombre del permiso para buscar las matrículas.
     * @return Un Optional que puede contener una lista de MatriculaVO si se encuentran.
     * 
     */
    public Optional<List<MatriculaVO>> buscarUsuariosCarnet(String nombre) {
        return mr.buscarUsuariosPermiso(nombre);
    }
    
    /**
     * Obtiene una lista de PermisoVO por el ID del usuario.
     * 
     * @param idusuario El ID del usuario para buscar los permisos.
     * @return Una lista de PermisoVO que coinciden con el criterio de búsqueda.
     * 
     */
    public List<PermisoVO> obtenerPermisosPorUsuarioId(int idusuario) {
        return mr.findPermisosByUsuarioId(idusuario);
    }
    
    /**
     * Verifica si un usuario está matriculado en un permiso.
     * 
     * @param usuario El usuario a verificar.
     * @param permiso El permiso a verificar.
     * @return Verdadero si el usuario está matriculado en el permiso, falso en caso contrario.
     * 
     */
    public boolean isUsuarioMatriculadoEnPermiso(UsuarioVO usuario, PermisoVO permiso) {
        return mr.existsByUsuarioIdusuarioAndPermisoIdpermiso(usuario.getIdusuario(), permiso.getIdpermiso());
    }

    /**
     * Elimina todas las matrículas asociadas a un usuario específico.
     * 
     * @param idusuario El ID del usuario para el cual se eliminarán todas las matrículas.
     * 
     */
	public void deleteAllByUsuarioId(int idusuario) {
        mr.deleteAllByUsuarioId(idusuario);
    }
	
	@Override
	public <S extends MatriculaVO> S save(S entity) {
		MatriculaVO matricula=new MatriculaVO();
		try {
			matricula=mr.save(entity);
		}catch(DataAccessException e) {
			if(e.getCause() instanceof DataIntegrityViolationException) {
				System.out.println("Error: No se ha insertado la matrícula porque el usuario indicado ya estaba matriculado en ese tipo de carnet");
			}
		}
		return (S) matricula;
	}

	@Override
	public <S extends MatriculaVO> List<S> saveAll(Iterable<S> entities) {
		return mr.saveAll(entities);
	}

	@Override
	public <S extends MatriculaVO> Optional<S> findOne(Example<S> example) {
		return mr.findOne(example);
	}

	@Override
	public List<MatriculaVO> findAll(Sort sort) {
		return mr.findAll(sort);
	}

	@Override
	public void flush() {
		mr.flush();
	}

	@Override
	public Page<MatriculaVO> findAll(Pageable pageable) {
		return mr.findAll(pageable);
	}

	@Override
	public <S extends MatriculaVO> S saveAndFlush(S entity) {
		return mr.saveAndFlush(entity);
	}

	@Override
	public <S extends MatriculaVO> List<S> saveAllAndFlush(Iterable<S> entities) {
		return mr.saveAllAndFlush(entities);
	}

	@Override
	public List<MatriculaVO> findAll() {
		return mr.findAll();
	}

	@Override
	public List<MatriculaVO> findAllById(Iterable<Integer> ids) {
		return mr.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<MatriculaVO> entities) {
		mr.deleteInBatch(entities);
	}

	@Override
	public <S extends MatriculaVO> Page<S> findAll(Example<S> example, Pageable pageable) {
		return mr.findAll(example, pageable);
	}

	@Override
	public Optional<MatriculaVO> findById(Integer id) {
		return mr.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<MatriculaVO> entities) {
		mr.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return mr.existsById(id);
	}

	@Override
	public <S extends MatriculaVO> long count(Example<S> example) {
		return mr.count(example);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		mr.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends MatriculaVO> boolean exists(Example<S> example) {
		return mr.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		mr.deleteAllInBatch();
	}

	@Override
	public MatriculaVO getOne(Integer id) {
		return mr.getOne(id);
	}

	@Override
	public <S extends MatriculaVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return mr.findBy(example, queryFunction);
	}

	@Override
	public long count() {
		return mr.count();
	}

	@Override
	public void deleteById(Integer id) {
		mr.deleteById(id);
	}

	@Override
	public MatriculaVO getById(Integer id) {
		return mr.getById(id);
	}

	@Override
	public void delete(MatriculaVO entity) {
		mr.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		mr.deleteAllById(ids);
	}

	@Override
	public MatriculaVO getReferenceById(Integer id) {
		return mr.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends MatriculaVO> entities) {
		mr.deleteAll(entities);
	}

	@Override
	public <S extends MatriculaVO> List<S> findAll(Example<S> example) {
		return mr.findAll(example);
	}

	@Override
	public <S extends MatriculaVO> List<S> findAll(Example<S> example, Sort sort) {
		return mr.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		mr.deleteAll();
	}
	
	
}
