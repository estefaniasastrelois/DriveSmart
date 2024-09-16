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

import com.drivesmart.modelo.PermisoVO;
import com.drivesmart.servicios.ServicioPermiso;

/**
 * Controlador REST para gestionar los permisos.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@RestController
@RequestMapping("/permiso")
public class PermisoWS {
    @Autowired
    private ServicioPermiso stc;

    /**
     * Crea un nuevo permiso.
     * 
     * @param permiso El permiso a crear.
     * @return Una respuesta con el permiso creado o un mensaje de error.
     */
    @PostMapping("/add")
    public ResponseEntity<?> createPermiso(@RequestBody PermisoVO permiso) {
        PermisoVO permisoGuardado = null;
        Map<String, Object> response = new HashMap<>();
        try {
            permisoGuardado = stc.save(permiso);
            response.put("message", "Permiso creado exitosamente");
            response.put("Permiso", permisoGuardado);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al guardar el permiso: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina un permiso por su ID.
     * 
     * @param id El ID del permiso a eliminar.
     * @return Una respuesta con un mensaje de éxito o error.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePermiso(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            stc.deleteById(id);
            response.put("message", "Se ha eliminado el permiso");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "No se ha podido eliminar el permiso: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza un permiso por su ID.
     * 
     * @param permiso El permiso con los datos actualizados.
     * @param id El ID del permiso a actualizar.
     * @return Una respuesta con el permiso actualizado o un mensaje de error.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePermiso(@RequestBody PermisoVO permiso, @PathVariable int id) {
        PermisoVO permisoToUpdate = stc.findById(id).orElse(null);
        PermisoVO updatedPermiso;
        Map<String, Object> response = new HashMap<>();
        if (permisoToUpdate == null) {
            response.put("message", "Error al modificar el permiso: No se encontró el permiso a modificar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            // Actualizar propiedades del permiso según necesidad
            permisoToUpdate.setNombre(permiso.getNombre());
            permisoToUpdate.setDescripcion(permiso.getDescripcion());
            updatedPermiso = stc.save(permisoToUpdate);
            response.put("message", "El permiso se ha actualizado correctamente");
            return new ResponseEntity<PermisoVO>(updatedPermiso, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al modificar el permiso: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene todos los permisos.
     * 
     * @return Una respuesta con la lista de permisos o un mensaje de error.
     */
    @GetMapping("/listado")
    public ResponseEntity<?> getAllPermisos() {
    	List<PermisoVO> permisos=new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            permisos = stc.findAll();
            return new ResponseEntity<List<PermisoVO>>(permisos, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al mostrar el listado de permisos: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene un permiso por su ID.
     * 
     * @param id El ID del permiso a obtener.
     * @return Una respuesta con el permiso obtenido o un mensaje de error.
     */
    @GetMapping("/listado/{id}")
    public ResponseEntity<?> getPermisoById(@PathVariable int id) {
        PermisoVO permiso = null;
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<PermisoVO> optionalPermiso = stc.findById(id);

            if (optionalPermiso.isPresent()) {
                permiso = optionalPermiso.get();
                return new ResponseEntity<PermisoVO>(permiso, HttpStatus.OK);
            } else {
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("message", "Se ha producido un error al obtener el permiso: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
