package com.drivesmart.driveSmart;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Suite de pruebas para ejecutar todas las pruebas de la aplicación.
 * 
 * Las pruebas incluyen:
 * - TestRol
 * - TestUsuario
 * - TestPermiso
 * - TestPractica
 * - TestMatricula
 * - TestTema
 * - TestTest
 * - TestRealiza
 * - TestPregunta
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 2.0
 */
@Suite
@SelectClasses({ TestRol.class, TestUsuario.class, TestPermiso.class, TestPractica.class, TestMatricula.class, TestTema.class,  TestTest.class, TestRealiza.class, TestPregunta.class})
public class AllTests {

}
