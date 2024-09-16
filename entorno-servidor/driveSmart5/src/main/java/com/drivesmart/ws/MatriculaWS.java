package com.drivesmart.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drivesmart.modelo.MatriculaVO;
import com.drivesmart.modelo.PermisoVO;
import com.drivesmart.modelo.UsuarioVO;
import com.drivesmart.servicios.ServicioMatricula;
import com.drivesmart.servicios.ServicioPermiso;
import com.drivesmart.servicios.ServicioUsuario;

/**
 * Controlador REST para gestionar las matrículas.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@RestController
@RequestMapping("/matricula")
public class MatriculaWS {
	@Autowired
	private ServicioMatricula sm;
	@Autowired
	private ServicioUsuario su;
	@Autowired
	private ServicioPermiso sp;
	
	/**
     * Crea una nueva matrícula.
     * 
     * @param matricula La matrícula a crear.
     * @return Una respuesta con la matrícula creada o un mensaje de error.
     * 
     */
	@PostMapping("/add")
	public ResponseEntity<?> createMatricula(@RequestBody MatriculaVO matricula) {
	    MatriculaVO matriculaGuardada = null;
	    Map<String, Object> response = new HashMap<>();
	    try {
	        matriculaGuardada = sm.save(matricula);
	        response.put("message", "Matrícula creada exitosamente");
	        response.put("matricula", matriculaGuardada);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        response.put("message", "Error al guardar la matrícula: " + e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	/**
     * Elimina una matrícula por su ID.
     * 
     * @param id El ID de la matrícula a eliminar.
     * @return Una respuesta con un mensaje de éxito o error.
     * 
     */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteMatricula(@PathVariable Integer id){
		Map<String,Object> response=new HashMap<>();
		try {
			sm.deleteById(id);
			response.put("message", "Se ha eliminado la matrícula");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		}catch(Exception e) {
			response.put("message", "No se ha podido eliminar la matrícula: "+e.getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
     * Actualiza una matrícula por su ID.
     * 
     * @param matr La matrícula con los datos actualizados.
     * @param id El ID de la matrícula a actualizar.
     * @return Una respuesta con la matrícula actualizada o un mensaje de error.
     * 
     */
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateMatricula(@RequestBody MatriculaVO matr, @PathVariable Integer id){
		MatriculaVO matrToUpdate = sm.findById(id).get();
		MatriculaVO updatedMatr;
		Map<String,Object> response = new HashMap<>();
		if(matrToUpdate == null) {
			response.put("message", "Error al modificar la matricula: No se encontró la matrícula a modificar");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		try {
			matrToUpdate.setPermiso(matr.getPermiso());
			matrToUpdate.setUsuario(matr.getUsuario());
			updatedMatr=sm.save(matrToUpdate);
			response.put("message", "La matricula se ha actualizado correctamente");
			return new ResponseEntity<MatriculaVO>(updatedMatr, HttpStatus.OK);
		}catch(Exception e) {
			response.put("message", "Error al modificar la matricula: "+e.getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
     * Obtiene todas las matrículas.
     * 
     * @return Una respuesta con la lista de matrículas o un mensaje de error.
     * 
     */
    @GetMapping("/listado")
    public ResponseEntity<?> getAllMatriculas() {
    	List<MatriculaVO> matriculas=new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            matriculas = sm.findAll();
            return new ResponseEntity<List<MatriculaVO>>(matriculas, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al mostrar el listado de matrículas: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
    /**
     * Obtiene una matrícula por su ID.
     * 
     * @param id El ID de la matrícula a obtener.
     * @return Una respuesta con la matrícula obtenida o un mensaje de error.
     * 
     */
	@GetMapping("/listado/{id}")
	public ResponseEntity<?> getMatriculaById(@PathVariable Integer id){
		MatriculaVO matr=null;
		Map<String, Object> response = new HashMap<>();
		 
		try {
	    	Optional<MatriculaVO> optionalMatricula=sm.findById(id);
	    	
	    	if(optionalMatricula.isPresent()) {
	    		matr=optionalMatricula.get();
	    		return new ResponseEntity<MatriculaVO>(matr,HttpStatus.OK);
	    	}else {
	    		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
	    	}
	    } catch (Exception e) {
	        response.put("message", "Se ha producido un error al obtener la matricula: "+e.getMessage());
	        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	/**
	 * Busca matrículas por el nombre del usuario.
	 * 
	 * @param nombre El nombre del usuario.
	 * @return Una respuesta con la lista de matrículas del usuario o un mensaje de error.
	 * 
	 */
	@GetMapping("/usuariosPorCarnet/{nombre}")
    public ResponseEntity<?> buscaTestPorUsuario(@PathVariable String nombre) {
    	List<MatriculaVO> usuarios=new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            usuarios = sm.buscarUsuariosCarnet(nombre).get();
            return new ResponseEntity<List<MatriculaVO>>(usuarios, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Se ha producido un error al obtener los usuarios matriculados en un carnet específico: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	/**
	 * Obtiene los permisos de un usuario por su ID.
	 * 
	 * @param idusuario El ID del usuario.
	 * @return Una respuesta con la lista de permisos del usuario o un mensaje de error.
	 * 
	 */
	@GetMapping("/{idusuario}/permisos")
    public ResponseEntity<List<PermisoVO>> obtenerPermisos(@PathVariable int idusuario) {
        List<PermisoVO> permisos = sm.obtenerPermisosPorUsuarioId(idusuario);
        if (permisos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(permisos);
        }
    }
	
	/**
	 * Verifica si un usuario está matriculado en un permiso.
	 * 
	 * @param usuarioId El ID del usuario.
	 * @param permisoId El ID del permiso.
	 * @return Una respuesta con la verificación o un mensaje de error.
	 * 
	 */
	@GetMapping("/isMatriculado/{usuarioId}/{permisoId}")
    public ResponseEntity<?> isUsuarioMatriculadoEnPermiso(@PathVariable int usuarioId, @PathVariable int permisoId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UsuarioVO> usuario = su.findById(usuarioId);
            Optional<PermisoVO> permiso = sp.findById(permisoId);

            if (usuario.isPresent() && permiso.isPresent()) {
                boolean isMatriculado = sm.isUsuarioMatriculadoEnPermiso(usuario.get(), permiso.get());
                response.put("isMatriculado", isMatriculado);
                int idMatricula = sm.findByUsuarioAndTipocarnet(usuario.get(), permiso.get()).get().getIdmatricula();
                response.put("idMatricula", idMatricula);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "Usuario o Permiso no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("message", "Error al verificar la matrícula: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	/**
	 * Elimina todas las matrículas de un usuario por su ID.
	 * 
	 * @param idusuario El ID del usuario.
	 * @return Una respuesta con un mensaje de éxito o error.
	 * 
	 */
	@DeleteMapping("/delete/all/{idusuario}")
    public ResponseEntity<?> deleteAllMatriculasByUsuarioId(@PathVariable Integer idusuario){
        Map<String,Object> response=new HashMap<>();
        try {
            sm.deleteAllByUsuarioId(idusuario);
            response.put("message", "Se han eliminado todas las matrículas del usuario");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
        }catch(Exception e) {
            response.put("message", "No se han podido eliminar las matrículas del usuario: "+e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
