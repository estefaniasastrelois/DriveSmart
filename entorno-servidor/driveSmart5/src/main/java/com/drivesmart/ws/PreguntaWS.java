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

import com.drivesmart.modelo.PreguntaVO;
import com.drivesmart.modelo.TestVO;
import com.drivesmart.servicios.ServicioPregunta;

/**
 * Controlador REST para gestionar las preguntas.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@RestController
@RequestMapping("/pregunta")
public class PreguntaWS {
    @Autowired
    private ServicioPregunta sp;

    /**
     * Crea una nueva pregunta.
     * 
     * @param pregunta La pregunta a crear.
     * @return Una respuesta con la pregunta creada o un mensaje de error.
     */
    @PostMapping("/add")
    public ResponseEntity<?> createPregunta(@RequestBody PreguntaVO pregunta) {
        PreguntaVO preguntaGuardada = null;
        Map<String, Object> response = new HashMap<>();
        try {
            preguntaGuardada = sp.save(pregunta);
            response.put("message", "Pregunta creada exitosamente");
            response.put("pregunta", preguntaGuardada);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al guardar la pregunta: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina una pregunta por su ID.
     * 
     * @param id El ID de la pregunta a eliminar.
     * @return Una respuesta con un mensaje de confirmación o error.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePregunta(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            sp.deleteById(id);
            response.put("message", "Se ha eliminado la pregunta");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "No se ha podido eliminar la pregunta: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza una pregunta existente.
     * 
     * @param pregunta Los nuevos datos de la pregunta.
     * @param id El ID de la pregunta a actualizar.
     * @return Una respuesta con la pregunta actualizada o un mensaje de error.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePregunta(@RequestBody PreguntaVO pregunta, @PathVariable int id) {
        PreguntaVO preguntaToUpdate = sp.findById(id).orElse(null);
        PreguntaVO updatedPregunta;
        Map<String, Object> response = new HashMap<>();
        if (preguntaToUpdate == null) {
            response.put("message", "Error al modificar la pregunta: No se encontró la pregunta a modificar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            // Actualizar propiedades de la pregunta según necesidad
            preguntaToUpdate.setEnunciado(pregunta.getEnunciado());
            preguntaToUpdate.setTest(pregunta.getTest());
            updatedPregunta = sp.save(preguntaToUpdate);
            response.put("message", "La pregunta se ha actualizado correctamente");
            return new ResponseEntity<PreguntaVO>(updatedPregunta, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al modificar la pregunta: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene todas las preguntas.
     * 
     * @return Una respuesta con la lista de preguntas o un mensaje de error.
     */
    @GetMapping("/listado")
    public ResponseEntity<?> getAllPreguntas() {
    	List<PreguntaVO> preguntas=new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            preguntas = sp.findAll();
            return new ResponseEntity<List<PreguntaVO>>(preguntas, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al mostrar el listado de preguntas " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene una pregunta por su ID.
     * 
     * @param id El ID de la pregunta a obtener.
     * @return Una respuesta con la pregunta o un mensaje de error.
     */
    @GetMapping("/listado/{id}")
    public ResponseEntity<?> getPreguntaById(@PathVariable int id) {
        PreguntaVO pregunta = null;
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<PreguntaVO> optionalPregunta = sp.findById(id);

            if (optionalPregunta.isPresent()) {
                pregunta = optionalPregunta.get();
                return new ResponseEntity<PreguntaVO>(pregunta, HttpStatus.OK);
            } else {
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("message", "Se ha producido un error al obtener la pregunta: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene las preguntas asociadas a un test específico.
     * 
     * @param TestIdtest El ID del test.
     * @return Una respuesta con la lista de preguntas o un mensaje de error.
     */
    @GetMapping("/findByTestIdtest")
    public ResponseEntity<List<PreguntaVO>> findByTestIdtest(
            @RequestParam("TestIdtest") int TestIdtest) {
        
        List<PreguntaVO> tests = sp.findByTestIdtest(TestIdtest);
        
        if (tests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(tests, HttpStatus.OK);
        }
    }
    
    /**
     * Obtiene el ID más alto entre todas las preguntas.
     * 
     * @return Una respuesta con el ID más alto o un mensaje de error.
     */
    @GetMapping("/maxId")
    public ResponseEntity<?> getMaxIdPregunta() {
        Map<String, Object> response = new HashMap<>();
        try {
            Integer maxId = sp.findMaxIdPregunta();
            response.put("maxId", maxId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al obtener el máximo id de pregunta: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Elimina todas las preguntas asociadas a un test específico.
     * 
     * @param idtest El ID del test.
     * @return Una respuesta con un mensaje de confirmación o error.
     */
	 @DeleteMapping("/deleteByTest/{idtest}")
	    public ResponseEntity<?> eliminarPreguntasDeTest(@PathVariable int idtest) {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            sp.eliminarPreguntasDeTest(idtest);
	            response.put("message", "Todas las preguntas del test han sido eliminadas");
	            return new ResponseEntity<>(response, HttpStatus.OK);
	        } catch (Exception e) {
	            response.put("message", "Error al eliminar las preguntas del test: " + e.getMessage());
	            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
}
