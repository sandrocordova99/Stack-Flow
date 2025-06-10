package com.desarrollo.repository;

import com.desarrollo.model.ProyectoEntity;
import com.desarrollo.model.TareaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TareaRepository extends JpaRepository<TareaEntity , Long> {

    Set<TareaEntity> findTareaEntityByProyectosId(Long id);

/*
    boolean existeTareaPorNombre(String nombreTareas);
*/

    @Query(value = "SELECT nombre FROM tarea WHERE nombre = ?",nativeQuery = true)
    String buscarTareaPorNombre(String nombre);

    Optional<TareaEntity> findByNombre(String nombre);

}
