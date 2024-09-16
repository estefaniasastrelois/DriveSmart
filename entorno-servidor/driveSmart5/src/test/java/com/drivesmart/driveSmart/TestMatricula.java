package com.drivesmart.driveSmart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.drivesmart.modelo.MatriculaVO;
import com.drivesmart.modelo.PermisoVO;
import com.drivesmart.modelo.UsuarioVO;
import com.drivesmart.serviciosImpl.ServicioMatriculaImpl;
import com.drivesmart.serviciosImpl.ServicioPermisoImpl;
import com.drivesmart.serviciosImpl.ServicioUsuarioImpl;

/**
 * Clase de prueba para la entidad Matricula.
 * 
 * Esta clase contiene pruebas para los métodos de la clase ServicioMatriculaImpl.
 *  
 * @author Estefanía Sastre
 * @version 5.0
 * @since 2.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestMatricula {
	@Autowired
	private ServicioMatriculaImpl sm;
	@Autowired
	private ServicioUsuarioImpl su;
	@Autowired
	private ServicioPermisoImpl stc;
	
	/**
     * Prueba para el método de inserción de matrícula.
     */
	@Test
	public void test01InsertarMatricula() {
		UsuarioVO usu1=su.findByDni("65738229B").get();
		UsuarioVO usu2=su.findByDni("76465899L").get();
		PermisoVO tipoB=stc.findByNombre("B").get();
		PermisoVO tipoA1=stc.findByNombre("A1").get();
		sm.save(new MatriculaVO(usu1,tipoB));
		sm.save(new MatriculaVO(usu2,tipoB));
		assertNotNull(sm.save(new MatriculaVO(usu2,tipoA1)));
	}
	
	/**
     * Prueba para el método de modificación de matrícula.
     */
	@Test
	public void test02ModificarMatricula() {
		MatriculaVO matricula=sm.findById(3).get();
		matricula.setPermiso(stc.findByNombre("C1").get());
		assertEquals("C1",sm.save(matricula).getPermiso().getNombre());
	}
	
	/**
     * Prueba para el método de eliminación de matrícula.
     */
	@Test
	public void test03EliminarMatricula() {
		MatriculaVO matricula=sm.findById(3).get();
		sm.delete(matricula);
		assertTrue(sm.findById(3).isEmpty());
	}
	
	/**
     * Prueba para el método que obtiene todas las matrículas.
     */
	@Test
	public void test04findAll() {
		assertEquals(2,sm.findAll().size());
	}
	
	/**
     * Prueba para el método que encuentra una matrícula por usuario y tipo de carnet.
     */
	@Test
	public void test05findByUsuarioTipoCarnet() {
		UsuarioVO usu2=su.findByDni("76465899L").get();
		PermisoVO tipoB=stc.findByNombre("B").get();
		assertTrue(sm.findByUsuarioAndTipocarnet(usu2, tipoB).isPresent());
	}
	
	/**
     * Prueba para el método que busca usuarios por carnet.
     */
	@Test
	public void test06usuariosPorCarnet() {
		assertEquals(2,sm.buscarUsuariosCarnet("B").get().size());
	}
	
	/**
     * Prueba para el método que obtiene permisos por ID de usuario.
     */
	@Test
	public void test07obtenerPermisosPorUsuarioId() {
		UsuarioVO usu1=su.findByDni("65738229B").get();
		List<PermisoVO> permisos = sm.obtenerPermisosPorUsuarioId(usu1.getIdusuario());
		assertNotNull(permisos);
		assertFalse(permisos.isEmpty());
		assertEquals("B", permisos.get(0).getNombre());
	}
	
	/**
     * Prueba para el método que verifica si un usuario está matriculado en un permiso.
     */
	@Test
	public void test08isUsuarioMatriculadoEnPermiso() {
		UsuarioVO usu2=su.findByDni("76465899L").get();
		PermisoVO tipoB=stc.findByNombre("B").get();
		assertTrue(sm.isUsuarioMatriculadoEnPermiso(usu2, tipoB));
		PermisoVO tipoA1=stc.findByNombre("A1").get();
		assertFalse(sm.isUsuarioMatriculadoEnPermiso(usu2, tipoA1));
	}
	
	/**
     * Prueba para el método que elimina todas las matrículas por ID de usuario.
     */
	@Test
	public void test09deleteAllByUsuarioId() {
		UsuarioVO usu1=su.findByDni("65738229B").get();
		sm.deleteAllByUsuarioId(usu1.getIdusuario());
		assertTrue(sm.findByUsuarioAndTipocarnet(usu1, stc.findByNombre("B").get()).isEmpty());
	}
}
