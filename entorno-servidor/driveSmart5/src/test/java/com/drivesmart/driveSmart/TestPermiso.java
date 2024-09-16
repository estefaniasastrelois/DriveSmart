package com.drivesmart.driveSmart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.drivesmart.modelo.PermisoVO;
import com.drivesmart.serviciosImpl.ServicioPermisoImpl;

/**
 * Clase de prueba para la entidad Permiso.
 * 
 * Esta clase contiene pruebas para los métodos de la clase ServicioPermisoImpl.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 2.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestPermiso {
	@Autowired
	private ServicioPermisoImpl stc;
	
	/**
     * Prueba para el método de inserción de tipo de carnet.
     */
	@Test
	public void test01InsertarTipoCarnet() {
		stc.save(new PermisoVO("A1","DescripcionA1"));
		stc.save(new PermisoVO("A3","DescripcionA3"));
		stc.save(new PermisoVO("C1","DescripcionC1"));
		assertNotNull(stc.save(new PermisoVO("B","DescripcionB")));
	}
	
	/**
     * Prueba para el método de modificación de tipo de carnet.
     */
	@Test
	public void test02ModificarTipoCarnet() {
		PermisoVO tipocarnet=stc.findById(2).get();
		tipocarnet.setNombre("A2");
		assertEquals("A2",stc.save(tipocarnet).getNombre());
	}
	
	/**
     * Prueba para el método de eliminación de tipo de carnet.
     */
	@Test
	public void test03EliminarTipoCarnet() {
		PermisoVO tipocarnet=stc.findById(2).get();
		stc.delete(tipocarnet);
		assertTrue(stc.findById(2).isEmpty());
	}
	
	/**
     * Prueba para el método que obtiene todos los tipos de carnet.
     */
	@Test
	public void test04findAll() {
		assertEquals(3,stc.findAll().size());
	}
	
	/**
     * Prueba para el método que obtiene un tipo de carnet por nombre.
     */
	@Test
	public void test05findByNombre() {
		assertTrue(stc.findByNombre("B").isPresent());
	}
}
