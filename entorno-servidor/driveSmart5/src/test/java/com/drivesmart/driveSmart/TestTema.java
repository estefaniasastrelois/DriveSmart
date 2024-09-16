package com.drivesmart.driveSmart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.drivesmart.modelo.TemaVO;
import com.drivesmart.serviciosImpl.ServicioTemaImpl;

/**
 * Clase de prueba para la entidad Tema.
 * 
 * Esta clase contiene pruebas para los métodos de la clase ServicioTemaImpl.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 3.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestTema {
	@Autowired
	private ServicioTemaImpl st;
	
	/**
     * Prueba para el método de inserción de tema.
     */
	@Test
	public void test01InsertarTema() {
		st.save(new TemaVO("Señales de tráfico"));
		st.save(new TemaVO("Seguridad vial y accidentes"));
		assertNotNull(st.save(new TemaVO("Maniobras e ")));
	}
	
	/**
     * Prueba para el método de modificación de tema.
     */
	@Test
	public void test02ModificarTema() {
		TemaVO tema=st.findById(3).get();
		tema.setNombre("Maniobras e intersecciones");
		assertEquals("Maniobras e intersecciones",st.save(tema).getNombre());
	}
	
	/**
     * Prueba para el método de eliminación de tema.
     */
	@Test
	public void test03EliminarTema() {
		TemaVO tema=st.findById(3).get();
		st.delete(tema);
		assertTrue(st.findById(3).isEmpty());
	}
	
	/**
     * Prueba para el método que obtiene todos los temas.
     */
	@Test
	public void test04findAll() {
		assertEquals(2,st.findAll().size());
	}
	
	/**
     * Prueba para el método que encuentra un tema por nombre.
     */
	@Test
	public void test05findByNombre() {
		assertTrue(st.findByNombre("Señales de tráfico").isPresent());
	}

}
