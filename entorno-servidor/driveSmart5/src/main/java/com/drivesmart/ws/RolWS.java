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

import com.drivesmart.modelo.RolVO;
import com.drivesmart.servicios.ServicioRol;

/**
 * Controlador REST para gestionar los roles.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@RestController
@RequestMapping("/rol")
public class RolWS {
	@Autowired
	private ServicioRol sr;
	
	/**
     * Crea un nuevo rol.
     * 
     * @param rol El rol a crear.
     * @return Una respuesta con el rol creado o un mensaje de error.
     */
	@PostMapping("/add")
	public ResponseEntity<?> createRol(@RequestBody RolVO rol) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        RolVO rolNuevo = sr.save(rol);
	        response.put("message", "Rol creado exitosamente");
	        response.put("rol", rolNuevo);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        response.put("message", "Error al insertar el rol: " + e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	/**
     * Elimina un rol existente.
     * 
     * @param id El ID del rol a eliminar.
     * @return Una respuesta con un mensaje de éxito o un mensaje de error.
     */	
	@DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRol(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            sr.deleteById(id);
            response.put("message", "Se ha eliminado el rol");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "No se ha podido eliminar el rol: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	/**
     * Actualiza un rol existente.
     * 
     * @param rol El rol con los datos actualizados.
     * @param id El ID del rol a actualizar.
     * @return Una respuesta con el rol actualizado o un mensaje de error.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRol(@RequestBody RolVO rol, @PathVariable int id) {
        RolVO rolToUpdate = sr.findById(id).orElse(null);
        RolVO updatedRol;
        Map<String, Object> response = new HashMap<>();
        if (rolToUpdate == null) {
            response.put("message", "Error al modificar el rol: No se encontró el rol a modificar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            // Actualizar propiedades del rol según necesidad
            rolToUpdate.setNombre(rol.getNombre());
            updatedRol = sr.save(rolToUpdate);
            response.put("message", "El rol se ha actualizado correctamente.");
            return new ResponseEntity<RolVO>(updatedRol, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al modificar el rol: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene todos los roles existentes.
     * 
     * @return Una respuesta con la lista de roles o un mensaje de error.
     */
    @GetMapping("/listado")
    public ResponseEntity<?> getAllRoles() {
    	List<RolVO> roles=new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            roles = sr.findAll();
            return new ResponseEntity<List<RolVO>>(roles, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al mostrar el listado de roles: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene un rol por su ID.
     * 
     * @param id El ID del rol a obtener.
     * @return Una respuesta con el rol o un mensaje de error.
     */
	@GetMapping("/listado/{id}")
	public ResponseEntity<?> getRolById(@PathVariable Integer id){
		RolVO rol=null;
		Map<String, Object> response = new HashMap<>();
		 
		try {
	    	Optional<RolVO> optionalRol=sr.findById(id);
	    	
	    	if(optionalRol.isPresent()) {
	    		rol=optionalRol.get();
	    		return new ResponseEntity<RolVO>(rol,HttpStatus.OK);
	    	}else {
	    		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
	    	}
	    } catch (Exception e) {
	        response.put("message", "Se ha producido un error al obtener el rol: "+e.getMessage());
	        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}
