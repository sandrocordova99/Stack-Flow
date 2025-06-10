package com.desarrollo.repository.userRepository;

import com.desarrollo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.username =?1")
   UserEntity buscarUsuarioPorNombre(String nombre);

   //Validar username
   @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.username = :username")
   boolean existsByUsername(@Param("username") String username);

   // Validad DNIHarol
   @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.dni = :dni")
   boolean existsByDni(@Param("dni") String dni);

   // Validar email
   @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.email = :email")
   boolean existsByEmail(@Param("email") String email);

   UserEntity findByUsername(String username);


}
