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

import com.drivesmart.modelo.UsuarioVO;
import com.drivesmart.servicios.ServicioUsuario;

/**
 * Controlador REST para gestionar los usuarios.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioWS {
    @Autowired
    private ServicioUsuario su;

    /**
     * Crea un nuevo usuario.
     * 
     * @param usuario El usuario a crear.
     * @return Una respuesta con el usuario creado o un mensaje de error.
     */
    @PostMapping("/add")
    public ResponseEntity<?> createUsuario(@RequestBody UsuarioVO usuario) {
    	Map<String, Object> response = new HashMap<>();
	    try {
	        UsuarioVO usuarioNuevo = su.save(usuario);
	        response.put("message", "Usuario creado exitosamente");
	        response.put("usuario", usuarioNuevo);
	        
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        response.put("message", "Error al insertar el usuario: " + e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
    }

    /**
     * Elimina un usuario existente.
     * 
     * @param id El ID del usuario a eliminar.
     * @return Una respuesta con un mensaje de éxito o un mensaje de error.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            su.deleteById(id);
            response.put("message", "Se ha eliminado el usuario");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "No se ha podido eliminar el usuario: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza un usuario existente.
     * 
     * @param usuario El usuario con los datos actualizados.
     * @param id El ID del usuario a actualizar.
     * @return Una respuesta con el usuario actualizado o un mensaje de error.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUsuario(@RequestBody UsuarioVO usuario, @PathVariable int id) {
        UsuarioVO usuarioToUpdate = su.findById(id).orElse(null);
        UsuarioVO updatedUsuario;
        Map<String, Object> response = new HashMap<>();
        if (usuarioToUpdate == null) {
            response.put("message", "Error al modificar el usuario: No se encontró el usuario a modificar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            // Actualizar propiedades del usuario según necesidad
            usuarioToUpdate.setApellidos(usuario.getApellidos());
            usuarioToUpdate.setDni(usuario.getDni());
            usuarioToUpdate.setEmail(usuario.getEmail());
            usuarioToUpdate.setNombre(usuario.getNombre());
            usuarioToUpdate.setPassword(usuario.getPassword());
            //usuarioToUpdate.setRol(usuario.getRol());
            usuarioToUpdate.setTelefono(usuario.getTelefono());
            updatedUsuario = su.save(usuarioToUpdate);
            response.put("message", "El usuario se ha actualizado correctamente.");
            return new ResponseEntity<UsuarioVO>(updatedUsuario, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al modificar el usuario: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene todos los usuarios existentes.
     * 
     * @return Una respuesta con la lista de usuarios o un mensaje de error.
     */
    @GetMapping("/listado")
    public ResponseEntity<?> getAllUsuarios() {
        List<UsuarioVO> usuarios = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            usuarios = su.findAll();
            return new ResponseEntity<List<UsuarioVO>>(usuarios, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al mostrar el listado de usuarios: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene un usuario por su ID.
     * 
     * @param id El ID del usuario a obtener.
     * @return Una respuesta con el usuario o un mensaje de error.
     */
    @GetMapping("/listado/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable int id) {
        UsuarioVO usuario = null;
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UsuarioVO> optionalUsuario = su.findById(id);

            if (optionalUsuario.isPresent()) {
                usuario = optionalUsuario.get();
                return new ResponseEntity<UsuarioVO>(usuario, HttpStatus.OK);
            } else {
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("message", "Se ha producido un error al obtener el usuario por id: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene un usuario por su email.
     * 
     * @param email El email del usuario a obtener.
     * @return Una respuesta con el usuario o un mensaje de error.
     */
    @GetMapping("/listado/comprueba/{email}")
    public ResponseEntity<?> getUsuarioByEmail(@PathVariable String email) {
        UsuarioVO usuario = null;
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UsuarioVO> optionalUsuario = su.findByEmail(email);

            if (optionalUsuario.isPresent()) {
                usuario = optionalUsuario.get();
                return new ResponseEntity<UsuarioVO>(usuario, HttpStatus.OK);
            } else {
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("message", "Se ha producido un error al obtener el usuario por email: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

