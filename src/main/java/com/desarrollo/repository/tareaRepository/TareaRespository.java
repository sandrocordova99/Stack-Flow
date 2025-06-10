package com.desarrollo.repository.tareaRepository;

import com.desarrollo.model.ProyectoEntity;
import com.desarrollo.model.TareaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TareaRespository extends JpaRepository<TareaEntity,Long> {

   @Query(value = "SELECT nombre FROM tarea WHERE nombre = ?",nativeQuery = true)
    String buscarTareaPorNombre(String nombre);

    Optional<TareaEntity> findByNombre(String nombre);

    List<TareaEntity> findByProyectosIn(Set<ProyectoEntity> proyectosDelUsuario);
}
