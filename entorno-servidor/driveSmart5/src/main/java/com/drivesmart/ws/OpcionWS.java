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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drivesmart.modelo.OpcionVO;
import com.drivesmart.servicios.ServicioOpcion;

/**
 * Controlador REST para gestionar las opciones.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@RestController
@RequestMapping("/opcion")
public class OpcionWS {
    @Autowired
    private ServicioOpcion so;
    
    /**
     * Crea una nueva opción.
     * 
     * @param opcion La opción a crear.
     * @return Una respuesta con la opción creada o un mensaje de error.
     */
    @PostMapping("/add")
    public ResponseEntity<?> createOpcion(@RequestBody OpcionVO opcion) {
        OpcionVO opcionGuardada = null;
        Map<String, Object> response = new HashMap<>();
        try {
        	opcionGuardada = so.save(opcion);
            response.put("message", "Opcion creada exitosamente");
            response.put("opcion", opcionGuardada);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al guardar la opcion: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Elimina una opción por su ID.
     * 
     * @param id El ID de la opción a eliminar.
     * @return Una respuesta con un mensaje de éxito o error.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOpcion(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            so.deleteById(id);
            response.put("message", "Se ha eliminado la opcion");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "No se ha podido eliminar la opcion: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Actualiza una opción por su ID.
     * 
     * @param opcion La opción con los datos actualizados.
     * @param id El ID de la opción a actualizar.
     * @return Una respuesta con la opción actualizada o un mensaje de error.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOpcion(@RequestBody OpcionVO opcion, @PathVariable int id) {
        OpcionVO opcionToUpdate = so.findById(id).orElse(null);
        OpcionVO updatedOpcion;
        Map<String, Object> response = new HashMap<>();
        if (opcionToUpdate == null) {
            response.put("message", "Error al modificar la opcion: No se encontró la opcion a modificar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            // Actualizar propiedades de la opcion según necesidad
            opcionToUpdate.setRespuesta(opcion.getRespuesta());
            opcionToUpdate.setCorrecta(opcion.getCorrecta());
            // Otros campos que necesites actualizar...

            // Guardar la opcion actualizada en la base de datos
            updatedOpcion = so.save(opcionToUpdate);
            response.put("message", "La opcion se ha actualizado correctamente");
            return new ResponseEntity<OpcionVO>(updatedOpcion, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al modificar la opcion: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene todas las opciones.
     * 
     * @return Una respuesta con la lista de opciones o un mensaje de error.
     */
    @GetMapping("/listado")
    public ResponseEntity<?> getAllOpcions() {
    	List<OpcionVO> opcions=new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            opcions = so.findAll();
            return new ResponseEntity<List<OpcionVO>>(opcions, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al mostrar el listado de opcions " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene una opción por su ID.
     * 
     * @param id El ID de la opción a obtener.
     * @return Una respuesta con la opción obtenida o un mensaje de error.
     */
    @GetMapping("/listado/{id}")
    public ResponseEntity<?> getOpcionById(@PathVariable int id) {
        OpcionVO opcion = null;
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<OpcionVO> optionalOpcion = so.findById(id);

            if (optionalOpcion.isPresent()) {
                opcion = optionalOpcion.get();
                return new ResponseEntity<OpcionVO>(opcion, HttpStatus.OK);
            } else {
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("message", "Se ha producido un error al obtener la opcion: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene las opciones por el ID de la pregunta.
     * 
     * @param PreguntaIdpregunta El ID de la pregunta.
     * @return Una respuesta con la lista de opciones de la pregunta o un mensaje de error.
     */
    @GetMapping("/findByPreguntaIdpregunta")
    public ResponseEntity<List<OpcionVO>> findByPreguntaIdpregunta(
    		@RequestParam("PreguntaIdpregunta") int PreguntaIdpregunta){
    	List<OpcionVO> preguntas = so.findByPreguntaIdpregunta(PreguntaIdpregunta);
    	
    	if(preguntas.isEmpty()) {
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(preguntas, HttpStatus.OK);
        }
    }
    
    /**
     * Obtiene el ID máximo de las opciones.
     * 
     * @return Una respuesta con el ID máximo de las opciones o un mensaje de error.
     */
    @GetMapping("/maxId")
    public ResponseEntity<?> getMaxIdOpcion() {
        Map<String, Object> response = new HashMap<>();
        try {
            Integer maxId = so.findMaxIdOpcion();
            response.put("maxId", maxId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al obtener el máximo id de opción: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Elimina todas las opciones de una pregunta por su ID.
     * 
     * @param idpregunta El ID de la pregunta.
     * @return Una respuesta con un mensaje de éxito o error.
     */
    @DeleteMapping("/deleteByPregunta/{idpregunta}")
    public ResponseEntity<?> eliminarOpcionesDePregunta(@PathVariable int idpregunta) {
        Map<String, Object> response = new HashMap<>();
        try {
            so.eliminarOpcionesDePregunta(idpregunta);
            response.put("message", "Todas las opciones de la pregunta han sido eliminadas");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al eliminar las opciones de la pregunta: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
