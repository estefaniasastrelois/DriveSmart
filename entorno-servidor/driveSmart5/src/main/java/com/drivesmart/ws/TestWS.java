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

import com.drivesmart.modelo.TestVO;
import com.drivesmart.servicios.ServicioTest;

/**
 * Controlador REST para gestionar los test.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@RestController
@RequestMapping("/test")
public class TestWS {
    @Autowired
    private ServicioTest st;

    /**
     * Crea un nuevo test.
     * 
     * @param test El test a crear.
     * @return Una respuesta con el test creado o un mensaje de error.
     */
    @PostMapping("/add")
    public ResponseEntity<?> createTest(@RequestBody TestVO test) {
        TestVO testGuardado = null;
        Map<String, Object> response = new HashMap<>();
        try {
            testGuardado = st.save(test);
            response.put("message", "Test creado exitosamente");
            response.put("test", testGuardado);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al guardar el test: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina un test existente.
     * 
     * @param id El ID del test a eliminar.
     * @return Una respuesta con un mensaje de éxito o un mensaje de error.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTest(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            st.deleteById(id);
            response.put("message", "Se ha eliminado el test");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "No se ha podido eliminar el test: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza un test existente.
     * 
     * @param test El test con los datos actualizados.
     * @param id El ID del test a actualizar.
     * @return Una respuesta con el test actualizado o un mensaje de error.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTest(@RequestBody TestVO test, @PathVariable int id) {
        TestVO testToUpdate = st.findById(id).orElse(null);
        TestVO updatedTest;
        Map<String, Object> response = new HashMap<>();
        if (testToUpdate == null) {
            response.put("message", "Error al modificar el test: No se encontró el test a modificar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            // Actualizar propiedades del test según necesidad
            testToUpdate.setAmbito(test.getAmbito());
            testToUpdate.setReferencia(test.getReferencia());
            testToUpdate.setPermiso(test.getPermiso());
            updatedTest = st.save(testToUpdate);
            response.put("message", "El test se ha actualizado correctamente.");
            return new ResponseEntity<TestVO>(updatedTest, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al modificar el test: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene todos los tests existentes.
     * 
     * @return Una respuesta con la lista de tests o un mensaje de error.
     */
    @GetMapping("/listado")
    public ResponseEntity<?> getAllTest() {
    	List<TestVO> test=new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            test = st.findAll();
            return new ResponseEntity<List<TestVO>>(test, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al mostrar el listado de test: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene un test por su ID.
     * 
     * @param id El ID del test a obtener.
     * @return Una respuesta con el test o un mensaje de error.
     */
    @GetMapping("/listado/{id}")
    public ResponseEntity<?> getTestById(@PathVariable int id) {
        TestVO test = null;
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<TestVO> optionalTest = st.findById(id);

            if (optionalTest.isPresent()) {
                test = optionalTest.get();
                return new ResponseEntity<TestVO>(test, HttpStatus.OK);
            } else {
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("message", "Se ha producido un error al obtener el test: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene una lista de tests por el nombre del permiso y el ámbito.
     * 
     * @param PermisoNombre El nombre del permiso.
     * @param Ambito El ámbito.
     * @return Una respuesta con la lista de tests o un mensaje de error.
     */
    @GetMapping("/findByPermisoNombreAndAmbito")
    public ResponseEntity<List<TestVO>> findByPermisoNombreAndAmbito(
            @RequestParam("PermisoNombre") String PermisoNombre,
            @RequestParam("Ambito") String Ambito) {
        
        List<TestVO> tests = st.findByPermisoNombreAndAmbito(PermisoNombre, Ambito);
        
        if (tests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(tests, HttpStatus.OK);
        }
    }
    
}
