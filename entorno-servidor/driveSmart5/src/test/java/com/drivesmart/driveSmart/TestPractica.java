package com.drivesmart.driveSmart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.drivesmart.modelo.PermisoVO;
import com.drivesmart.modelo.PracticaVO;
import com.drivesmart.modelo.UsuarioVO;
import com.drivesmart.serviciosImpl.ServicioPermisoImpl;
import com.drivesmart.serviciosImpl.ServicioPracticaImpl;
import com.drivesmart.serviciosImpl.ServicioUsuarioImpl;

/**
 * Clase de prueba para la entidad Practica.
 * 
 * Esta clase contiene pruebas para los métodos de la clase ServicioPracticaImpl.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 2.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestPractica {
	@Autowired
	private ServicioPracticaImpl sp;
	@Autowired
	private ServicioUsuarioImpl su;
	@Autowired
	private ServicioPermisoImpl spm;
	
	/**
     * Prueba para el método de inserción de práctica.
     */
	@Test
    public void test01InsertarPractica() {
        UsuarioVO usu2 = su.findByDni("76465899L").get();
        PermisoVO permiso = spm.findById(1).get();
        sp.save(new PracticaVO(LocalDateTime.of(LocalDate.of(2023, 10, 01), LocalTime.of(10, 00)), "Pachin de Melas", usu2, permiso));
        sp.save(new PracticaVO(LocalDateTime.of(LocalDate.of(2023, 10, 02), LocalTime.of(10, 00)), "Playa del Arbeyal", usu2, permiso));
        assertNotNull(sp.save(new PracticaVO(LocalDateTime.of(LocalDate.of(2023, 10, 03), LocalTime.of(10, 00)), "El Molinon", usu2, permiso)));
    }

	/**
     * Prueba para el método de modificación de práctica.
     */
	@Test
	public void test02ModificarPractica() {
		PracticaVO practica=sp.findById(3).get();
		practica.setFechahora(LocalDateTime.of(LocalDate.of(2023, 10, 03), LocalTime.of(11, 00)));
		assertEquals(LocalDateTime.of(LocalDate.of(2023, 10, 03), LocalTime.of(11, 00)),sp.save(practica).getFechahora());
	}
	
	 /**
     * Prueba para el método de eliminación de práctica.
     */
	@Test
	public void test03EliminarPractica() {
		PracticaVO practica=sp.findById(3).get();
		sp.delete(practica);
		assertTrue(sp.findById(3).isEmpty());
	}
	
	 /**
     * Prueba para el método que obtiene todas las prácticas.
     */
	@Test
	public void test04findAll() {
		assertEquals(2,sp.findAll().size());
	}
	
	/**
     * Prueba para el método que obtiene prácticas por ID de usuario ordenadas por fecha y hora.
     */
	@Test
    public void test05FindByUsuarioIdusuarioOrderByFechahoraAsc() {
        List<PracticaVO> practicas = sp.findByUsuarioIdusuarioOrderByFechahoraAsc(2);
        assertNotNull(practicas);
        assertEquals(2, practicas.size());
        assertTrue(practicas.get(0).getFechahora().isBefore(practicas.get(1).getFechahora()));
    }

	/**
     * Prueba para el método que obtiene prácticas por día.
     */
    @Test
    public void test06FindPracticasByDay() {
        LocalDateTime day = LocalDateTime.of(2023, 10, 02, 0, 0);
        List<PracticaVO> practicas = sp.findPracticasByDay(day);
        assertNotNull(practicas);
        assertEquals(1, practicas.size());
        assertEquals("Playa del Arbeyal", practicas.get(0).getLugar());
    }

    /**
     * Prueba para el método que obtiene prácticas por permiso.
     */
    @Test
    public void test07ObtenerPracticasPorPermiso() {
        List<PracticaVO> practicas = sp.obtenerPracticasPorPermiso(1);
        assertNotNull(practicas);
        assertEquals(2, practicas.size());
        assertTrue(practicas.get(0).getFechahora().isBefore(practicas.get(1).getFechahora()));
    }

    /**
     * Prueba para el método que obtiene prácticas por usuario y permiso.
     */
    @Test
    public void test08GetPracticasByUsuarioAndPermiso() {
        List<PracticaVO> practicas = sp.getPracticasByUsuarioAndPermiso(2, 1);
        assertNotNull(practicas);
        assertEquals(2, practicas.size());
        assertTrue(practicas.get(0).getFechahora().isBefore(practicas.get(1).getFechahora()));
    }

    /**
     * Prueba para el método que elimina prácticas de usuario.
     */
    @Test
    public void test09EliminarPracticasDeUsuario() {
        sp.eliminarPracticasDeUsuario(2);
        List<PracticaVO> practicas = sp.findByUsuarioIdusuarioOrderByFechahoraAsc(2);
        assertTrue(practicas.isEmpty());
    }
}
