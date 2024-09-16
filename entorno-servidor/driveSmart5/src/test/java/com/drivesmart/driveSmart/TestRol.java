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
import com.drivesmart.serviciosImpl.ServicioRolImpl;

/**
 * Clase de prueba para la entidad Rol.
 * 
 * Esta clase contiene pruebas para los métodos de la clase ServicioRolImpl.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 2.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestRol {
	@Autowired
	private ServicioRolImpl sr;
	
	/**
     * Prueba para el método de inserción de rol.
     */
	@Test
	public void test01InsertarTest() {
		sr.save(new RolVO("admin"));
		sr.save(new RolVO("estandar"));
		assertNotNull(sr.save(new RolVO("rol01")));
	}
	
	/**
     * Prueba para el método de modificación de rol.
     */
	@Test
	public void test02ModificarRol() {
		RolVO rol=sr.findById(3).get();
		rol.setNombre("rol02");
		assertEquals("rol02",sr.save(rol).getNombre());
	}
	
	/**
     * Prueba para el método de eliminación de rol.
     */
	@Test
	public void test03EliminarRol() {
		RolVO rol=sr.findById(3).get();
		sr.delete(rol);
		assertTrue(sr.findById(3).isEmpty());
	}

	/**
     * Prueba para el método que obtiene todos los roles.
     */
	@Test
	public void test04findAll() {
		assertEquals(2,sr.findAll().size());
	}
	
	/**
     * Prueba para el método que encuentra un rol por nombre.
     */
	@Test
	public void test05findByNombre() {
		assertTrue(sr.findByNombre("admin").isPresent());
	}
}
