package com.drivesmart.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.drivesmart.modelo.MatriculaVO;
import com.drivesmart.modelo.PermisoVO;
import com.drivesmart.modelo.UsuarioVO;

import jakarta.transaction.Transactional;

/**
 * Interfaz del repositorio para la entidad MatriculaVO.
 * Esta interfaz es utilizada por Spring Data JPA para crear una implementación del repositorio.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */

public interface MatriculaRepository extends JpaRepository<MatriculaVO, Integer> {
    /**
     * Busca una matrícula por usuario y permiso.
     * 
     * @param usuario El usuario de la matrícula.
     * @param permiso El permiso de la matrícula.
     * @return Un Optional que puede contener la matrícula si se encuentra.
     */
    Optional<MatriculaVO> findByUsuarioAndPermiso(UsuarioVO usuario,PermisoVO permiso);
    
    /**
     * Busca usuarios por permiso.
     * 
     * @param nombre El nombre del permiso.
     * @return Un Optional que puede contener una lista de matrículas si se encuentran.
     */
    @Query("select m from MatriculaVO m where m.permiso.nombre=:nombre")
    Optional<List<MatriculaVO>> buscarUsuariosPermiso(String nombre);
    
    /**
     * Encuentra permisos por ID de usuario.
     * 
     * @param idusuario El ID del usuario.
     * @return Una lista de permisos para el usuario dado.
     */
    @Query("select m.permiso from MatriculaVO m where m.usuario.idusuario = :idusuario")
    List<PermisoVO> findPermisosByUsuarioId(@Param("idusuario") int idusuario);
    
    /**
     * Verifica si un usuario está matriculado en un permiso específico.
     * 
     * @param idusuario El ID del usuario.
     * @param idpermiso El ID del permiso.
     * @return Verdadero si el usuario está matriculado en el permiso, falso en caso contrario.
     */
    boolean existsByUsuarioIdusuarioAndPermisoIdpermiso(int idusuario, int idpermiso);
    
    /**
     * Elimina todas las matrículas de un usuario.
     * 
     * @param idusuario El ID del usuario.
     */
    @Modifying
    @Transactional
    @Query("delete from MatriculaVO m where m.usuario.idusuario = :idusuario")
    void deleteAllByUsuarioId(@Param("idusuario") int idusuario);
}