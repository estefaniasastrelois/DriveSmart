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

import com.drivesmart.modelo.TemaVO;
import com.drivesmart.servicios.ServicioTema;

/**
 * Controlador REST para gestionar los temas.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 3.0
 */
@RestController
@RequestMapping("/tema")
public class TemaWS {
	@Autowired
	private ServicioTema st;
	
	/**
     * Crea un nuevo tema.
     * 
     * @param tema El tema a crear.
     * @return Una respuesta con el tema creado o un mensaje de error.
     */
	@PostMapping("/add")
	public ResponseEntity<?> createTema(@RequestBody TemaVO tema) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        TemaVO temaNuevo = st.save(tema);
	        response.put("message", "tema creado exitosamente");
	        response.put("tema", temaNuevo);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        response.put("message", "Error al insertar el tema: " + e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	/**
     * Elimina un tema existente.
     * 
     * @param id El ID del tema a eliminar.
     * @return Una respuesta con un mensaje de éxito o un mensaje de error.
     */
	@DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTema(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            st.deleteById(id);
            response.put("message", "Se ha eliminado el tema");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "No se ha podido eliminar el tema: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	/**
     * Actualiza un tema existente.
     * 
     * @param tema El tema con los datos actualizados.
     * @param id El ID del tema a actualizar.
     * @return Una respuesta con el tema actualizado o un mensaje de error.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTema(@RequestBody TemaVO tema, @PathVariable int id) {
        TemaVO temaToUpdate = st.findById(id).orElse(null);
        TemaVO updatedtema;
        Map<String, Object> response = new HashMap<>();
        if (temaToUpdate == null) {
            response.put("message", "Error al modificar el tema: No se encontró el tema a modificar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            // Actualizar propiedades del tema según necesidad
            temaToUpdate.setNombre(tema.getNombre());
            updatedtema = st.save(temaToUpdate);
            response.put("message", "El tema se ha actualizado correctamente.");
            return new ResponseEntity<TemaVO>(updatedtema, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al modificar el tema: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene todos los temas existentes.
     * 
     * @return Una respuesta con la lista de temas o un mensaje de error.
     */
    @GetMapping("/listado")
    public ResponseEntity<?> getAllTemas() {
    	List<TemaVO> temaes=new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            temaes = st.findAll();
            return new ResponseEntity<List<TemaVO>>(temaes, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al mostrar el listado de temas: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene un tema por su ID.
     * 
     * @param id El ID del tema a obtener.
     * @return Una respuesta con el tema o un mensaje de error.
     */
	@GetMapping("/listado/{id}")
	public ResponseEntity<?> getTemaById(@PathVariable Integer id){
		TemaVO tema=null;
		Map<String, Object> response = new HashMap<>();
		 
		try {
	    	Optional<TemaVO> optionaltema=st.findById(id);
	    	
	    	if(optionaltema.isPresent()) {
	    		tema=optionaltema.get();
	    		return new ResponseEntity<TemaVO>(tema,HttpStatus.OK);
	    	}else {
	    		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
	    	}
	    } catch (Exception e) {
	        response.put("message", "Se ha producido un error al obtener el tema: "+e.getMessage());
	        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}
