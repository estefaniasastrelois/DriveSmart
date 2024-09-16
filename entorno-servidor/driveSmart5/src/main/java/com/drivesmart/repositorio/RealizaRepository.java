package com.drivesmart.repositorio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.drivesmart.modelo.RealizaVO;
import com.drivesmart.modelo.UsuarioVO;

import jakarta.transaction.Transactional;

/**
 * Interfaz del repositorio para la entidad RealizaVO.
 * Esta interfaz es utilizada por Spring Data JPA para crear una implementación del repositorio.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface RealizaRepository extends JpaRepository<RealizaVO, Integer>{
	/**
     * Busca una realización por usuario y fecha y hora.
     * 
     * @param usuario El usuario de la realización.
     * @param fechahora La fecha y hora de la realización.
     * @return Un Optional que puede contener la realización si se encuentra.
     */
	Optional<RealizaVO> findByUsuarioAndFechahora(UsuarioVO usuario, LocalDateTime fechahora);
	
	 /**
     * Devuelve la lista de test realizados por un usuario.
     * 
     * @param idusuario El ID del usuario.
     * @return Un Optional que puede contener la lista de realizaciones si se encuentran.
     */
	@Query("select r from RealizaVO r where r.usuario.idusuario=:idusuario")
	Optional<List<RealizaVO>> buscarTestAlumno(int idusuario);
	
	 /**
     * Cuenta el total de test realizados por un usuario.
     * 
     * @param idUsuario El ID del usuario.
     * @return El total de test realizados por el usuario.
     */
	@Query("select count(r) from RealizaVO r where r.usuario.id = :idUsuario")
	long recuentoTestByUsuarioId(@Param("idUsuario") int idUsuario);
	
	/**
	 * Cuenta el número total de test aprobados por un usuario.
	 * 
	 * @param idUsuario El ID del usuario.
	 * @return El total de test aprobados por el usuario.
	 */
	@Query("select count(r) from RealizaVO r where r.usuario.id = :idUsuario and r.nota >= 27")
    long recuentoTestAprobadosByUsuarioId(@Param("idUsuario") int idUsuario);
	
	/**
	 * Cuenta el número total de test suspensos por un usuario.
	 * 
	 * @param idUsuario El ID del usuario.
	 * @return El total de test suspensos por el usuario.
	 */
    @Query("select count(r) from RealizaVO r where r.usuario.id = :idUsuario and r.nota <= 26")
    long recuentoTestSuspensosByUsuarioId(@Param("idUsuario") int idUsuario);
    
    /**
     * Calcula la media de fallos en los test de un usuario.
     * 
     * @param idUsuario El ID del usuario.
     * @return La media de fallos en los test del usuario.
     */
    @Query("SELECT AVG(30 - r.nota) FROM RealizaVO r WHERE r.usuario.id = :idUsuario")
    Double mediaFallosByUsuarioId(@Param("idUsuario") int idUsuario);    
    
    /**
     * Calcula el total del número de test por temas que ha realizado un usuario.
     * 
     * @param idUsuario El ID del usuario.
     * @return El total de test por temas realizados por el usuario.
     */
    @Query("select count(r) from RealizaVO r where r.usuario.id = :idUsuario and r.test.referencia like '%T%'")
    long countTestByTema(@Param("idUsuario") int idUsuario);
    
    /**
     * Calcula el total del número de test globales que ha realizado un usuario.
     * 
     * @param idUsuario El ID del usuario.
     * @return El total de test globales realizados por el usuario.
     */
    @Query("select count(r) from RealizaVO r where r.usuario.id = :idUsuario and r.test.referencia like '%G%'")
    long countTestGlobales(@Param("idUsuario") int idUsuario);
    
    /**
     * Calcula el porcentaje de test aprobados por un usuario.
     * 
     * @param idUsuario El ID del usuario.
     * @return El porcentaje de test aprobados por el usuario.
     */
    @Query("select count(r) * 1.0 / (select count(r2) from RealizaVO r2 where r2.usuario.id = :idUsuario) * 100 from RealizaVO r where r.usuario.id = :idUsuario and r.nota >= 27")
    Double porcentajeTestAprobados(@Param("idUsuario") int idUsuario);
    
    /**
     * Calcula el porcentaje de test suspensos por un usuario.
     * 
     * @param idUsuario El ID del usuario.
     * @return El porcentaje de test suspensos por el usuario.
     */
    @Query("select count(r) * 1.0 / (select count(r2) from RealizaVO r2 where r2.usuario.id = :idUsuario) * 100 from RealizaVO r where r.usuario.id = :idUsuario and r.nota < 27")
    Double porcentajeTestSuspensos(@Param("idUsuario") int idUsuario);
    
    /**
     * Selecciona el nombre del tema cuyos test ha suspendido más veces un usuario.
     * 
     * @param idUsuario El ID del usuario.
     * @return Un Optional que puede contener el nombre del tema si se encuentra.
     */
    @Query("SELECT t.nombre " +
            "FROM TemaVO t " +
            "WHERE t.idtema = (SELECT r.test.tema.idtema " +
                              "FROM RealizaVO r " +
                              "WHERE r.test.tema IS NOT NULL AND r.nota < 27 AND r.usuario.idusuario = :idUsuario " +
                              "GROUP BY r.test.tema.idtema " +
                              "ORDER BY COUNT(r) DESC, r.test.tema.idtema ASC " +
                              "LIMIT 1)")
    Optional<String> temaMasSuspendido(@Param("idUsuario") int idUsuario);
    
    /**
     * Selecciona el nombre del tema cuyos test ha aprobado más veces un usuario.
     * 
     * @param idUsuario El ID del usuario.
     * @return Un Optional que puede contener el nombre del tema si se encuentra.
     */
    @Query("SELECT t.nombre " +
            "FROM TemaVO t " +
            "WHERE t.idtema = (" +
            "  SELECT r.test.tema.idtema " +
            "  FROM RealizaVO r " +
            "  WHERE r.usuario.idusuario = :idUsuario AND r.nota >= 27 AND r.test.tema IS NOT NULL " +
            "  GROUP BY r.test.tema.idtema " +
            "  ORDER BY COUNT(r) DESC, r.test.tema.idtema DESC " +
            "  LIMIT 1)")
    Optional<String> temaMasAprobado(@Param("idUsuario") int idUsuario);
    
    /**
     * Calcula el número de aprobados del mes actual por un usuario.
     * 
     * @param idUsuario El ID del usuario.
     * @return El número de test aprobados este mes por el usuario.
     */
    @Query("SELECT COUNT(r)" +
    		"FROM RealizaVO r "+
    		"WHERE r.usuario.id = :idUsuario AND r.nota >= 27 AND MONTH(r.fechahora) = MONTH(CURRENT_DATE) "+
    		"AND YEAR(r.fechahora) = YEAR(CURRENT_DATE)")
    long countAprobadosEsteMes(@Param("idUsuario") int idUsuario);
    
    /**
     * Calcula el número de aprobados del mes pasado por un usuario.
     * 
     * @param idUsuario El ID del usuario.
     * @return El número de test aprobados el mes pasado por el usuario.
     */
    @Query("SELECT COUNT(r) "+
    		"FROM RealizaVO r "+
    		"WHERE r.usuario.id = :idUsuario AND r.nota >= 27 AND MONTH(r.fechahora) = MONTH(CURRENT_DATE) - 1 "+
    		"AND YEAR(r.fechahora) = YEAR(CURRENT_DATE)")
    long countAprobadosMesPasado(@Param("idUsuario") int idUsuario);
    
    /**
     * Método que calcula el número de veces que un usuario ha practicado un tema.
     * 
     * @param idUsuario El ID del usuario.
     * @return Una lista de objetos, donde cada objeto es un array que contiene el nombre del tema y el número de veces que el usuario ha practicado ese tema.
     */
    @Query("SELECT t.nombre, (SELECT COUNT(r) " +
            					" FROM RealizaVO r, TestVO test " +
            					" WHERE r.test.idtest = test.idtest " +
            					" AND test.tema.idtema = t.idtema " +
            					" AND r.usuario.id = :idUsuario) AS count " +
            "FROM TemaVO t " +
            "ORDER BY count ASC")
     List<Object[]> countTestsByTemaForUser(int idUsuario);
     
     /**
      * Método que calcula el porcentaje que se ha practicado cada tema (incluidos los no practicados nunca, que devolverá 0%).
      * 
      * @param idUsuario El ID del usuario.
      * @return Una lista de objetos, donde cada objeto es un array que contiene el nombre del tema y el número de veces que el usuario ha practicado ese tema.
      */
     @Query("SELECT t.nombre, " +
    	       "(SELECT COUNT(r) FROM RealizaVO r WHERE r.test.tema.idtema = t.idtema AND r.usuario.id = :idUsuario) " +
    	       "FROM TemaVO t")
     List<Object[]> countTestsByTema(@Param("idUsuario") int idUsuario);
     /**
      * Método para eliminar todos los registros de la tabla realiza del usuario indicado.
      * 
      * @param idusuario El ID del usuario.
      */
     @Modifying
     @Transactional
     @Query("delete from RealizaVO p where p.usuario.idusuario = :idusuario")
     void deleteAllByIdusuario(int idusuario);
     /**
      * Método para eliminar todos los registros de la tabla realiza del test indicado.
      * 
      * @param idtest El ID del test.
      */
     @Modifying
     @Transactional
     @Query("delete from RealizaVO p where p.test.idtest = :idtest")
     void deleteAllByIdtest(int idtest);
}
