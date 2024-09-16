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

import com.drivesmart.modelo.RealizaVO;
import com.drivesmart.servicios.ServicioRealiza;

/**
 * Controlador REST para gestionar las realizaciones de test.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@RestController
@RequestMapping("/realiza")
public class RealizaWS {
    @Autowired
    private ServicioRealiza sr;

    /**
     * Crea un nuevo registro de realización.
     * 
     * @param realiza El registro de realización a crear.
     * @return Una respuesta con el registro de realización creado o un mensaje de error.
     */
    @PostMapping("/add")
    public ResponseEntity<?> createRealiza(@RequestBody RealizaVO realiza) {
        RealizaVO realizaGuardado = null;
        Map<String, Object> response = new HashMap<>();
        try {
            realizaGuardado = sr.save(realiza);
            response.put("message", "Registro de práctica creado exitosamente");
            response.put("realiza", realizaGuardado);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al guardar el registro de práctica: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina un registro de realización por su ID.
     * 
     * @param id El ID del registro de realización a eliminar.
     * @return Una respuesta con un mensaje de confirmación o error.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRealiza(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            sr.deleteById(id);
            response.put("message", "Se ha eliminado la relación realiza");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "No se ha podido eliminar la relación realiza: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza un registro de realización existente.
     * 
     * @param realiza Los nuevos datos del registro de realización.
     * @param id El ID del registro de realización a actualizar.
     * @return Una respuesta con el registro de realización actualizado o un mensaje de error.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRealiza(@RequestBody RealizaVO realiza, @PathVariable int id) {
        RealizaVO realizaToUpdate = sr.findById(id).orElse(null);
        RealizaVO updatedRealiza;
        Map<String, Object> response = new HashMap<>();
        if (realizaToUpdate == null) {
            response.put("message", "Error al modificar la relación realiza: No se encontró la relación a modificar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            // Actualizar propiedades de la relación realiza según necesidad
            realizaToUpdate.setFechahora(realiza.getFechahora());
            realizaToUpdate.setNota(realiza.getNota());
            realizaToUpdate.setTest(realiza.getTest());
            realizaToUpdate.setUsuario(realiza.getUsuario());
            updatedRealiza = sr.save(realizaToUpdate);
            response.put("message", "La realización se ha actualizado correctamente.");
            return new ResponseEntity<RealizaVO>(updatedRealiza, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al modificar la relación realiza: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene todos los registros de realización.
     * 
     * @return Una respuesta con la lista de registros de realización o un mensaje de error.
     */
    @GetMapping("/listado")
    public ResponseEntity<?> getAllRealiza() {
    	List<RealizaVO> realiza=new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            realiza = sr.findAll();
            return new ResponseEntity<List<RealizaVO>>(realiza, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al mostrar el listado de realizaciones: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene los tests realizados por un usuario específico.
     * 
     * @param idusuario El ID del usuario.
     * @return Una respuesta con la lista de tests realizados o un mensaje de error.
     */
    @GetMapping("/testPorUsuario/{idusuario}")
    public ResponseEntity<?> buscaTestPorUsuario(@PathVariable int idusuario) {
    	List<RealizaVO> realiza=new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            realiza = sr.buscarTestAlumno(idusuario).get();
            return new ResponseEntity<List<RealizaVO>>(realiza, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Se ha producido un error al obtener los test realizados por un usuario específico: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene un registro de realización por su ID.
     * 
     * @param id El ID del registro de realización a obtener.
     * @return Una respuesta con el registro de realización o un mensaje de error.
     */
    @GetMapping("/listado/{id}")
    public ResponseEntity<?> getRealizaById(@PathVariable int id) {
        RealizaVO realiza = null;
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<RealizaVO> optionalRealiza = sr.findById(id);

            if (optionalRealiza.isPresent()) {
                realiza = optionalRealiza.get();
                return new ResponseEntity<RealizaVO>(realiza, HttpStatus.OK);
            } else {
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("message", "Se ha producido un error al obtener la relación realiza: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene el recuento de tests realizados por un usuario específico.
     * 
     * @param idUsuario El ID del usuario.
     * @return Una respuesta con el recuento de tests o un mensaje de error.
     */
    @GetMapping("/recuentoTest/{idUsuario}")
    public ResponseEntity<?> recuentoTestByUsuarioId(@PathVariable int idUsuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            long count = sr.recuentoTestByUsuarioId(idUsuario);
            response.put("count", count);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al contar los tests realizados por el usuario: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene el recuento de tests aprobados realizados por un usuario específico.
     * 
     * @param idUsuario El ID del usuario.
     * @return Una respuesta con el recuento de tests aprobados o un mensaje de error.
     */
    @GetMapping("/recuentoTestAprobados/{idUsuario}")
    public ResponseEntity<?> recuentoTestAprobadosByUsuarioId(@PathVariable int idUsuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            long count = sr.recuentoTestAprobadosByUsuarioId(idUsuario);
            response.put("count", count);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al contar los tests aprobados por el usuario: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene el recuento de tests suspensos realizados por un usuario específico.
     * 
     * @param idUsuario El ID del usuario.
     * @return Una respuesta con el recuento de tests suspensos o un mensaje de error.
     */
    @GetMapping("/recuentoTestSuspensos/{idUsuario}")
    public ResponseEntity<?> recuentoTestSuspensosByUsuarioId(@PathVariable int idUsuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            long count = sr.recuentoTestSuspensosByUsuarioId(idUsuario);
            response.put("count", count);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al contar los tests suspensos por el usuario: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene la media de fallos realizados por un usuario específico.
     * 
     * @param idUsuario El ID del usuario.
     * @return La media de fallos.
     */
    @GetMapping("/mediaFallos/{idUsuario}")
    public Double mediaFallosByUsuarioId(@PathVariable int idUsuario) {
        return sr.mediaFallosByUsuarioId(idUsuario);
    }
    
    /**
     * Obtiene el recuento de tests por tema realizados por un usuario específico.
     * 
     * @param idUsuario El ID del usuario.
     * @return Una respuesta con el recuento de tests por tema o un mensaje de error.
     */
    @GetMapping("/countTestByTema/{idUsuario}")
    public ResponseEntity<?> countTestByTema(@PathVariable int idUsuario) {
        try {
            long count = sr.countTestByTema(idUsuario);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene el recuento de tests globales realizados por un usuario específico.
     * 
     * @param idUsuario El ID del usuario.
     * @return Una respuesta con el recuento de tests globales o un mensaje de error.
     */
    @GetMapping("/countTestGlobales/{idUsuario}")
    public ResponseEntity<?> countTestGlobales(@PathVariable int idUsuario) {
        try {
            long count = sr.countTestGlobales(idUsuario);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene el porcentaje de tests aprobados realizados por un usuario específico.
     * 
     * @param idUsuario El ID del usuario.
     * @return Una respuesta con el porcentaje de tests aprobados o un mensaje de error.
     */
    @GetMapping("/porcentajeTestAprobados/{idUsuario}")
    public ResponseEntity<?> porcentajeTestAprobados(@PathVariable int idUsuario) {
        try {
            Double porcentaje = sr.porcentajeTestAprobados(idUsuario);
            return new ResponseEntity<>(porcentaje, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene el porcentaje de tests suspendidos realizados por un usuario específico.
     * 
     * @param idUsuario El ID del usuario.
     * @return Una respuesta con el porcentaje de tests suspendidos o un mensaje de error.
     */
    @GetMapping("/porcentajeTestSuspensos/{idUsuario}")
    public ResponseEntity<?> porcentajeTestSuspensos(@PathVariable int idUsuario) {
        try {
            Double porcentaje = sr.porcentajeTestSuspensos(idUsuario);
            return new ResponseEntity<>(porcentaje, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene el tema más suspendido por un usuario específico.
     * 
     * @param idUsuario El ID del usuario.
     * @return Una respuesta con el tema más suspendido o un mensaje de error.
     */
    @GetMapping("/temaMasSuspendido/{idUsuario}")
    public ResponseEntity<?> temaMasSuspendido(@PathVariable int idUsuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<String> tema = sr.temaMasSuspendido(idUsuario);
            if (tema.isPresent()) {
                response.put("tema", tema.get());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "No se encontraron suspensos para el usuario.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("message", "Error al obtener el tema más suspendido: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene el tema más aprobado por un usuario específico.
     * 
     * @param idUsuario El ID del usuario.
     * @return Una respuesta con el tema más aprobado o un mensaje de error.
     */
    @GetMapping("/temaMasAprobado/{idUsuario}")
    public ResponseEntity<?> temaMasAprobado(@PathVariable int idUsuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<String> tema = sr.temaMasAprobado(idUsuario);
            if (tema.isPresent()) {
                response.put("tema", tema.get());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "No se encontraron aprobados para el usuario.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("message", "Error al obtener el tema más aprobado: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Comprueba si un usuario específico ha mejorado este mes.
     * 
     * @param idUsuario El ID del usuario.
     * @return Una respuesta con la información de si el usuario ha mejorado o un mensaje de error.
     */
    @GetMapping("/progresionMesActual/{idUsuario}")
    public ResponseEntity<?> hasImprovedThisMonth(@PathVariable int idUsuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean progresion = sr.progresionMesActual(idUsuario);
            response.put("progresion", progresion);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al comprobar la mejora del usuario: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene el tema menos practicado por un usuario específico.
     * 
     * @param idUsuario El ID del usuario.
     * @return Una respuesta con el tema menos practicado o un mensaje de error.
     */
    @GetMapping("/temaMenosPracticado/{idUsuario}")
    public ResponseEntity<?> temaMenosPracticado(@PathVariable int idUsuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<String> tema = sr.temaMenosPracticado(idUsuario);
            if (tema.isPresent()) {
                response.put("tema", tema.get());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "No se encontraron temas practicados para el usuario.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("message", "Error al obtener el tema menos practicado: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene el porcentaje de tests por tema realizados por un usuario específico.
     * 
     * @param idUsuario El ID del usuario.
     * @return Una respuesta con el porcentaje de tests por tema o un mensaje de error.
     */
    @GetMapping("/porcentajeTestsPorTema/{idUsuario}")
    public ResponseEntity<?> porcentajeTestsPorTema(@PathVariable int idUsuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Double> porcentajes = sr.porcentajeTestsPorTema(idUsuario);
            return new ResponseEntity<>(porcentajes, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al calcular el porcentaje de tests por tema: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Elimina todos los registros de realización de un usuario específico.
     * 
     * @param idusuario El ID del usuario.
     * @return Una respuesta con un mensaje de confirmación o error.
     */
    @DeleteMapping("/deleteByUsuario/{idusuario}")
    public ResponseEntity<?> eliminarRealizaDeUsuario(@PathVariable int idusuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            sr.eliminarRealizaDeUsuario(idusuario);
            response.put("message", "Todos los registros de la tabla realiza del usuario han sido eliminadas");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al eliminar los registros de la tabla realiza del usuario: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Elimina todos los registros de realización de un test específico.
     * 
     * @param idtest El ID del test.
     * @return Una respuesta con un mensaje de confirmación o error.
     */
    @DeleteMapping("/deleteByTest/{idtest}")
    public ResponseEntity<?> eliminarRealizaDeTest(@PathVariable int idtest) {
        Map<String, Object> response = new HashMap<>();
        try {
            sr.eliminarRealizaDeTest(idtest);
            response.put("message", "Todos los registros de la tabla realiza del test han sido eliminadas");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al eliminar los registros de la tabla realiza del test: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

