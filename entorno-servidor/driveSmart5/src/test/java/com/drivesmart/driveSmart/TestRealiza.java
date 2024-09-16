package com.drivesmart.driveSmart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.drivesmart.modelo.RealizaVO;
import com.drivesmart.modelo.TestVO;
import com.drivesmart.modelo.UsuarioVO;
import com.drivesmart.serviciosImpl.ServicioRealizaImpl;
import com.drivesmart.serviciosImpl.ServicioTestImpl;
import com.drivesmart.serviciosImpl.ServicioUsuarioImpl;

/**
 * Clase de prueba para la entidad Realiza.
 * 
 * Esta clase contiene pruebas para los métodos de la clase ServicioRealizaImpl.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 2.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestRealiza {
	@Autowired
	private ServicioRealizaImpl sr;
	@Autowired
	private ServicioUsuarioImpl su;
	@Autowired
	private ServicioTestImpl st;
	
	/**
     * Prueba para el método de inserción de realiza.
     */
	@Test
	public void test01InsertarRealiza() {
		UsuarioVO usu2=su.findByDni("76465899L").get();
		TestVO test1=st.findByReferencia("A1_G_1").get();
		TestVO test2=st.findByReferencia("A1_G_2").get();
		TestVO test4=st.findByReferencia("C1_T1_1").get();
		sr.save(new RealizaVO(usu2,test1,24,LocalDateTime.now()));
		sr.save(new RealizaVO(usu2,test2,26,LocalDateTime.of(LocalDate.of(2023, 10, 01),LocalTime.of(10,10))));
		assertNotNull(sr.save(new RealizaVO(usu2,test4,28,LocalDateTime.now())));
	}
	
	/**
     * Prueba para el método de modificación de realiza.
     */
	@Test
	public void test02ModificarRealiza() {
		RealizaVO realiza=sr.findById(1).get();
		realiza.setNota(23);
		assertEquals(23,sr.save(realiza).getNota());
	}
	
	/**
     * Prueba para el método de eliminación de realiza.
     */
	@Test
	public void test03EliminarRealiza() {
		RealizaVO realiza=sr.findById(1).get();
		sr.delete(realiza);
		assertTrue(sr.findById(1).isEmpty());
	}
	
	/**
     * Prueba para el método que obtiene todos los realiza.
     */
	@Test
	public void test04findAll() {
		assertEquals(2,sr.findAll().size());
	}
	
	/**
     * Prueba para el método que encuentra un realiza por usuario y fecha/hora.
     */
	@Test
	public void test05findByUsuarioFechaHora() {
		UsuarioVO usu2=su.findByDni("76465899L").get();
		LocalDateTime fechahora=LocalDateTime.of(LocalDate.of(2023, 10, 01),LocalTime.of(10,10));
		assertEquals(26,sr.findByUsuarioAndFechahora(usu2,fechahora).get().getNota());
	}
	
	/**
     * Prueba para el método que cuenta los test realizados por un usuario.
     */
	@Test
    public void test07recuentoTestByUsuarioId() {
        assertEquals(2, sr.recuentoTestByUsuarioId(2));
    }

	/**
     * Prueba para el método que cuenta los test aprobados por un usuario.
     */
    @Test
    public void test08recuentoTestAprobadosByUsuarioId() {
        assertEquals(1, sr.recuentoTestAprobadosByUsuarioId(2));
    }

    /**
     * Prueba para el método que cuenta los test suspensos por un usuario.
     */
    @Test
    public void test09recuentoTestSuspensosByUsuarioId() {
        assertEquals(1, sr.recuentoTestSuspensosByUsuarioId(2));
    }

    /**
     * Prueba para el método que calcula la media de fallos por usuario.
     */
    @Test
    public void test10mediaFallosByUsuarioId() {
        assertEquals(3.0, sr.mediaFallosByUsuarioId(2));
    }

    /**
     * Prueba para el método que cuenta los test por tema.
     */
    @Test
    public void test11countTestByTema() {
        assertEquals(1, sr.countTestByTema(2));
    }

    /**
     * Prueba para el método que cuenta los test globales.
     */
    @Test
    public void test12countTestGlobales() {
        assertEquals(1, sr.countTestGlobales(2));
    }

    /**
     * Prueba para el método que calcula el porcentaje de test aprobados.
     */
    @Test
    public void test13porcentajeTestAprobados() {
        assertEquals(50.0, sr.porcentajeTestAprobados(2));
    }

    /**
     * Prueba para el método que calcula el porcentaje de test suspensos.
     */
    @Test
    public void test14porcentajeTestSuspensos() {
        Double porcentaje = sr.porcentajeTestSuspensos(2);
        assertNotNull(porcentaje);
        assertEquals(50.0, porcentaje);
    }

    /**
     * Prueba para el método que cuenta los test aprobados este mes.
     */
    @Test
    public void test15countAprobadosEsteMes() {
        long count = sr.countAprobadosEsteMes(2);
        assertEquals(1, count);
    }

    /**
     * Prueba para el método que cuenta los test aprobados el mes pasado.
     */
    @Test
    public void test16countAprobadosMesPasado() {
        long count = sr.countAprobadosMesPasado(2);
        assertEquals(0, count);
    }

    /**
     * Prueba para el método que verifica la progresión del mes actual.
     */
    @Test
    public void test17progresionMesActual() {
        assertTrue(sr.progresionMesActual(2));
    }

    /**
     * Prueba para el método que obtiene el tema menos practicado.
     */
    @Test
    public void test18temaMenosPracticado() {
        Optional<String> tema = sr.temaMenosPracticado(2);
        assertTrue(tema.isPresent());
    }

    /**
     * Prueba para el método que calcula el porcentaje de tests por tema.
     */
    @Test
    public void test19porcentajeTestsPorTema() {
        Map<String, Double> porcentajes = sr.porcentajeTestsPorTema(2);
        assertNotNull(porcentajes);
        assertEquals(2, porcentajes.size());
        assertTrue(porcentajes.containsKey("Señales de tráfico"));
    }

    /**
     * Prueba para el método que elimina realiza de un usuario.
     */
    @Test
    public void test20eliminarRealizaDeUsuario() {
        sr.eliminarRealizaDeUsuario(2);
        long count = sr.recuentoTestByUsuarioId(2);
        assertEquals(0, count);
    }

    /**
     * Prueba para el método que elimina realiza de un test.
     */
    @Test
    public void test21eliminarRealizaDeTest() {
        sr.eliminarRealizaDeTest(1);
        assertEquals(0, sr.recuentoTestByUsuarioId(2));
    }
}