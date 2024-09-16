package com.drivesmart.ws;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drivesmart.modelo.PracticaVO;
import com.drivesmart.servicios.ServicioPractica;

/**
 * Controlador REST para gestionar las prácticas.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@RestController
@RequestMapping("/practica")
public class PracticaWS {
	@Autowired
	private ServicioPractica sp;
	
	/**
     * Crea una nueva práctica.
     * 
     * @param practica La práctica a crear.
     * @return Una respuesta con la práctica creada o un mensaje de error.
     */
	@PostMapping("/add")
	public ResponseEntity<?> createPractica(@RequestBody PracticaVO practica) {
	    PracticaVO practicaGuardada = null;
	    Map<String, Object> response = new HashMap<>();
	    try {
	        practicaGuardada = sp.save(practica);
	        response.put("message", "Práctica creada exitosamente");
	        response.put("practica", practicaGuardada);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        response.put("message", "Error al guardar la práctica: " + e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	/**
     * Elimina una práctica por su ID.
     * 
     * @param id El ID de la práctica a eliminar.
     * @return Una respuesta con un mensaje de éxito o error.
     */
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deletePractica(@PathVariable Integer id){
		Map<String,Object> response=new HashMap<>();
		try {
			sp.deleteById(id);
			response.put("message", "Se ha eliminado la práctica");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		}catch(Exception e) {
			response.put("message", "No se ha podido eliminar la práctica: "+e.getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
     * Actualiza una práctica por su ID.
     * 
     * @param practica La práctica con los datos actualizados.
     * @param id El ID de la práctica a actualizar.
     * @return Una respuesta con la práctica actualizada o un mensaje de error.
     */
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updatePost(@RequestBody PracticaVO practica, @PathVariable Integer id){
		PracticaVO practicaToUpdate = sp.findById(id).get();
		PracticaVO updatedPractica;
		Map<String,Object> response = new HashMap<>();
		if(practicaToUpdate == null) {
			response.put("message", "Error al modificar la práctica: No se encontró la práctica a modificar");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		try {
			practicaToUpdate.setFechahora(practica.getFechahora());
			practicaToUpdate.setLugar(practica.getLugar());
			practicaToUpdate.setUsuario(practica.getUsuario());
			updatedPractica=sp.save(practicaToUpdate);
			response.put("message", "La practica se ha actualizado correctamente.");
			return new ResponseEntity<PracticaVO>(updatedPractica, HttpStatus.OK);
		}catch(Exception e) {
			response.put("message", "Error al modificar la práctica: "+e.getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
     * Obtiene todas las prácticas.
     * 
     * @return Una respuesta con la lista de prácticas o un mensaje de error.
     */
    @GetMapping("/listado")
    public ResponseEntity<?> getAllPracticas() {
    	List<PracticaVO> practicas=new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            practicas = sp.findAll();
            return new ResponseEntity<List<PracticaVO>>(practicas, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al mostrar el listado de prácticas: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
    /**
     * Obtiene una práctica por su ID.
     * 
     * @param id El ID de la práctica a obtener.
     * @return Una respuesta con la práctica obtenida o un mensaje de error.
     */
	@GetMapping("/listado/{id}")
	public ResponseEntity<?> getPracticaById(@PathVariable Integer id){
		PracticaVO practica=null;
		Map<String, Object> response = new HashMap<>();
		 
		try {
	    	Optional<PracticaVO> optionalPractica=sp.findById(id);
	    	
	    	if(optionalPractica.isPresent()) {
	    		practica=optionalPractica.get();
	    		return new ResponseEntity<PracticaVO>(practica,HttpStatus.OK);
	    	}else {
	    		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
	    	}
	    } catch (Exception e) {
	        response.put("message", "Se ha producido un error al obtener la practica: "+e.getMessage());
	        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	/**
     * Obtiene las prácticas de un usuario ordenadas por fecha y hora en orden ascendente.
     * 
     * @param UsuarioIdUsuario El ID del usuario.
     * @return Una respuesta con la lista de prácticas o un mensaje de error.
     */
	@GetMapping("/findByUsuarioIdusuarioOrderByFechahoraAsc")
    public ResponseEntity<List<PracticaVO>> findByUsuarioIdusuarioOrderByFechahoraAsc(
            @RequestParam("UsuarioIdUsuario") int UsuarioIdUsuario) {
        
        List<PracticaVO> practicas = sp.findByUsuarioIdusuarioOrderByFechahoraAsc(UsuarioIdUsuario);
        
        if (practicas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(practicas, HttpStatus.OK);
        }
    }
	
	 /**
	 * Obtiene las prácticas de un día específico.
	 * 
	 * @param day El día para el que se quieren obtener las prácticas.
	 * @return Una respuesta con la lista de prácticas o un mensaje de error.
	 */
	@GetMapping("/findPracticasByDay")
	public ResponseEntity<List<PracticaVO>> findPracticasByDay(
	        @RequestParam("day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime day) {
	    
	    List<PracticaVO> practicas = sp.findPracticasByDay(day);
	    
	    if (practicas.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } else {
	        return new ResponseEntity<>(practicas, HttpStatus.OK);
	    }
	}
	
	/**
     * Obtiene las prácticas por permiso.
     * 
     * @param idpermiso El ID del permiso.
     * @return Una respuesta con la lista de prácticas o un mensaje de error.
     */
	@GetMapping("/permiso/{idpermiso}")
    public ResponseEntity<List<PracticaVO>> obtenerPracticasPorPermiso(@PathVariable int idpermiso) {
        List<PracticaVO> practicas = sp.obtenerPracticasPorPermiso(idpermiso);
        if (practicas.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(practicas);
        }
    }
	
	/**
     * Obtiene las prácticas de un usuario y permiso específicos.
     * 
     * @param idusuario El ID del usuario.
     * @param idpermiso El ID del permiso.
     * @return Una respuesta con la lista de prácticas o un mensaje de error.
     */
	 @GetMapping("/filtraUsuarioPermiso")
     public ResponseEntity<List<PracticaVO>> getPracticas(
            @RequestParam int idusuario,
            @RequestParam int idpermiso) {
        List<PracticaVO> practicas = sp.getPracticasByUsuarioAndPermiso(idusuario, idpermiso);
        return ResponseEntity.ok(practicas);
     }
	 
	 /**
     * Elimina las prácticas de un usuario.
     * 
     * @param idusuario El ID del usuario.
     * @return Una respuesta con un mensaje de éxito o error.
     */
	 @DeleteMapping("/deleteByUsuario/{idusuario}")
     public ResponseEntity<?> eliminarPracticasDeUsuario(@PathVariable int idusuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            sp.eliminarPracticasDeUsuario(idusuario);
            response.put("message", "Todas las prácticas del usuario han sido eliminadas");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al eliminar las prácticas del usuario: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
     }

}
