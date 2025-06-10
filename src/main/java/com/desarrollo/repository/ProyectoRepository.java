package com.desarrollo.repository;

import com.desarrollo.model.ProyectoEntity;
import com.desarrollo.model.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<ProyectoEntity , Long> {

    @Query("SELECT COUNT(u) > 0 FROM ProyectoEntity u WHERE u.nombre = :nombre")
    boolean existsByNombre(@Param("nombre") String nombre);

    @EntityGraph(attributePaths = {"usuarios"})
    List<ProyectoEntity> findByUsuarios_Id(Long usuarioId);

    @Query("SELECT p FROM ProyectoEntity p JOIN p.usuarios u WHERE u.username = :username")
    List<ProyectoEntity> findByUsername(@Param("username") String username);

}
