package com.drivesmart.driveSmart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.drivesmart.modelo.PreguntaVO;
import com.drivesmart.modelo.TestVO;
import com.drivesmart.serviciosImpl.ServicioPreguntaImpl;
import com.drivesmart.serviciosImpl.ServicioTestImpl;

/**
 * Clase de prueba para la entidad Pregunta.
 * 
 * Esta clase contiene pruebas para los métodos de la clase ServicioPreguntaImpl.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 2.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestPregunta {
	@Autowired
	private ServicioPreguntaImpl sp;
	@Autowired
	private ServicioTestImpl st;
	
	/**
     * Prueba para el método de inserción de pregunta.
     */
	@Test
    public void test01InsertarPregunta() {
        TestVO test1 = st.findByReferencia("A1_G_1").get();
        TestVO test2 = st.findByReferencia("A1_G_2").get();
        sp.save(new PreguntaVO("Enunciado1", test1));
        sp.save(new PreguntaVO("Enunciado2", test1));
        assertNotNull(sp.save(new PreguntaVO("Enunciado3", test2)));
    }

	/**
     * Prueba para el método de modificación de pregunta.
     */
    @Test
    public void test02ModificarPregunta() {
        PreguntaVO pregunta = sp.findById(3).get();
        pregunta.setEnunciado("Enunciado modificado");
        assertEquals("Enunciado modificado", sp.save(pregunta).getEnunciado());
    }

    /**
     * Prueba para el método de eliminación de pregunta.
     */
    @Test
    public void test03EliminarPregunta() {
        PreguntaVO pregunta = sp.findById(3).get();
        sp.delete(pregunta);
        assertTrue(sp.findById(3).isEmpty());
    }

    /**
     * Prueba para el método que obtiene todas las preguntas.
     */
    @Test
    public void test04findAll() {
        assertEquals(2, sp.findAll().size());
    }

    /**
     * Prueba para el método que obtiene una pregunta por enunciado y test.
     */
    @Test
    public void test05findByEnunciadoTest() {
        TestVO test1 = st.findByReferencia("A1_G_1").get();
        assertEquals("Enunciado2", sp.findByEnunciadoAndTest("Enunciado2", test1).get().getEnunciado());
    }

    /**
     * Prueba para el método que obtiene preguntas por ID de test.
     */
    @Test
    public void test06findByTestIdtest() {
        assertEquals(2, sp.findByTestIdtest(1).size());
    }

    /**
     * Prueba para el método que obtiene el ID máximo de pregunta.
     */
    @Test
    public void test07findMaxIdPregunta() {
        Integer maxId = sp.findMaxIdPregunta();
        assertNotNull(maxId);
        assertEquals(2, maxId);
    }

    /**
     * Prueba para el método que elimina preguntas de un test.
     */
    @Test
    public void test08eliminarPreguntasDeTest() {
        sp.eliminarPreguntasDeTest(1);
        assertEquals(0, sp.findByTestIdtest(1).size());
    }
}
