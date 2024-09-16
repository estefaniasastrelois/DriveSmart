package com.drivesmart.driveSmart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.drivesmart.modelo.RolVO;
import com.drivesmart.modelo.UsuarioVO;
import com.drivesmart.serviciosImpl.ServicioRolImpl;
import com.drivesmart.serviciosImpl.ServicioUsuarioImpl;

/**
 * Clase de prueba para la entidad Usuario.
 * 
 * Esta clase contiene pruebas para los métodos de la clase ServicioUsuarioImpl.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 2.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestUsuario {
	@Autowired
	private ServicioUsuarioImpl su;
	@Autowired
	private ServicioRolImpl sr;
	
	/**
     * Prueba para el método de inserción de usuario.
     */
	@Test
	public void test01InsertarUsuario() {
		RolVO rol=sr.findByNombre("admin").get();
		su.save(new UsuarioVO("Estefania","Sastre Lois","65738229B","estefania@gmail.com","hola","664536718",rol));
		RolVO rol2=sr.findByNombre("estandar").get();
		su.save(new UsuarioVO("Maria","Lopez Dieguez","76465899L","maria@gmail.com","adios","774635627",rol2));
		assertNotNull(su.save(new UsuarioVO("Diego","Gomez Gonzalez","74637826Q","diego@gmail.com","nunca","664532417",rol2)));
	}
	
	/**
     * Prueba para el método de modificación de usuario.
     */
	@Test
	public void test02ModificarUsuario() {
		UsuarioVO usuario=su.findById(3).get();
		usuario.setNombre("Pepe");
		assertEquals("Pepe",su.save(usuario).getNombre());
	}

	 /**
     * Prueba para el método de eliminación de usuario.
     */
	@Test
	public void test03EliminarUsuario() {
		UsuarioVO usuario=su.findById(3).get();
		su.delete(usuario);
		assertTrue(su.findById(3).isEmpty());
	}
	
	/**
     * Prueba para el método que obtiene todos los usuarios.
     */
	@Test
	public void test04findAll() {
		assertEquals(2,su.findAll().size());
	}
	
	/**
     * Prueba para el método que encuentra un usuario por DNI.
     */
	@Test
	public void test05findByDni() {
		assertEquals("Maria",su.findByDni("76465899L").get().getNombre());
	}
	
	/**
     * Prueba para el método que encuentra un usuario por email.
     */
	@Test
	public void test06findByEmail() {
		assertEquals("Maria",su.findByEmail("maria@gmail.com").get().getNombre());
	}
	
}
