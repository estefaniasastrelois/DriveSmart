package com.drivesmart.repositorio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.drivesmart.modelo.PracticaVO;

import jakarta.transaction.Transactional;

/**
 * Interfaz del repositorio para la entidad PracticaVO.
 * Esta interfaz es utilizada por Spring Data JPA para crear una implementación del repositorio.
 * 
 * @author Estefanía Sastre
 * @version 5.0
 * @since 1.0
 */
public interface PracticaRepository extends JpaRepository<PracticaVO, Integer>{
    /**
     * Filtra las prácticas de un usuario indicado, ordenadas por fecha.
     * 
     * @param idusuario El ID del usuario.
     * @return Una lista de prácticas para el usuario dado, ordenadas por fecha.
     */
    List<PracticaVO> findByUsuarioIdusuarioOrderByFechahoraAsc(int idusuario);
    
    /**
     * Filtra las prácticas del día indicado.
     * 
     * @param startOfDay El inicio del día.
     * @param endOfDay El final del día.
     * @return Una lista de prácticas para el día dado, ordenadas por fecha.
     */
    List<PracticaVO> findByFechahoraBetweenOrderByFechahoraAsc(LocalDateTime startOfDay, LocalDateTime endOfDay);
    
    /**
     * Filtra las prácticas por permiso.
     * 
     * @param idpermiso El ID del permiso.
     * @return Una lista de prácticas para el permiso dado, ordenadas por fecha.
     */
    List<PracticaVO> findByPermisoIdpermisoOrderByFechahoraAsc(int idpermiso);
    
    /**
     * Filtra las prácticas por usuario y permiso.
     * 
     * @param idusuario El ID del usuario.
     * @param idpermiso El ID del permiso.
     * @return Una lista de prácticas para el usuario y permiso dados, ordenadas por fecha.
     */
    List<PracticaVO> findByUsuarioIdusuarioAndPermisoIdpermisoOrderByFechahoraAsc(int idusuario, int idpermiso);
    
    /**
     * Elimina todas las prácticas de un usuario.
     * 
     * @param idusuario El ID del usuario.
     */
    @Modifying
    @Transactional
    @Query("delete from PracticaVO p where p.usuario.idusuario = :idusuario")
    void deleteAllByIdusuario(int idusuario);
}