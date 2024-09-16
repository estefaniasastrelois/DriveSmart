package com.drivesmart.driveSmart;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.drivesmart.modelo.TestVO;
import com.drivesmart.modelo.PermisoVO;
import com.drivesmart.serviciosImpl.ServicioTestImpl;
import com.drivesmart.serviciosImpl.ServicioPermisoImpl;

/**
 * Clase de prueba para la entidad Test.
 * 
 * Esta clase contiene pruebas para los métodos de la clase ServicioTestImpl.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 2.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestTest {
	@Autowired
	private ServicioTestImpl st;
	@Autowired
	private ServicioPermisoImpl stc;
	
	/**
     * Prueba para el método de inserción de test.
     */
	@Test
	public void test01InsertarTest() {
		PermisoVO tipoA1=stc.findByNombre("A1").get();
		PermisoVO tipoB=stc.findByNombre("B").get();
		PermisoVO tipoC1=stc.findByNombre("C1").get();
		st.save(new TestVO("A1_G_1","global",tipoA1));
		st.save(new TestVO("A1_G_2","global",tipoA1));
		st.save(new TestVO("B_G_1","global",tipoB));
		assertNotNull(st.save(new TestVO("C1_T1_1","temas",tipoC1)));
	}
	
	/**
     * Prueba para el método de modificación de test.
     */
	@Test
	public void test02ModificarTest() {
		TestVO test=st.findById(3).get();
		test.setAmbito("temas");
		test.setReferencia("T-B-01");
		assertEquals("temas",st.save(test).getAmbito());
	}
	
	/**
     * Prueba para el método de eliminación de test.
     */
	@Test
	public void test03EliminarTest() {
		TestVO test=st.findById(3).get();
		st.delete(test);
		assertTrue(st.findById(3).isEmpty());
	}
	
	/**
     * Prueba para el método que obtiene todos los tests.
     */
	@Test
	public void test04findAll() {
		assertEquals(3,st.findAll().size());
	}
	
	/**
     * Prueba para el método que encuentra un test por referencia.
     */
	@Test
	public void test05findByReferencia() {
		assertEquals("global",st.findByReferencia("A1_G_1").get().getAmbito());
	}
	
	/**
     * Prueba para el método que obtiene los tests por permiso y ámbito.
     */
	@Test
    public void test06getTestsByPermisoAndAmbito() {
        List<TestVO> tests = st.findByPermisoNombreAndAmbito("A1", "global");
        assertEquals(2, tests.size());
        for (TestVO test : tests) {
            assertEquals("A1", test.getPermiso().getNombre());
            assertEquals("global", test.getAmbito());
        }
    }
}
