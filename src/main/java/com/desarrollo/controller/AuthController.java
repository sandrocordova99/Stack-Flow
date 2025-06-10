package com.desarrollo.controller;

import com.desarrollo.dto.user.UserDTO;
import com.desarrollo.model.UserEntity;
import com.desarrollo.repository.userRepository.UserRepository;
import com.desarrollo.service.user.UserDetailsSeriveImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserDetailsSeriveImpl authService;
    private final UserRepository userRepository;

    @PostMapping("/registar")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Map<String, ?>> register(@RequestBody UserDTO userDTO) {
        System.out.println("DTO recibido: " + userDTO);
        Map<String, String> response = new HashMap<>();
        try {

            //Validación del username
            if (authService.isUsernameRegistered(userDTO.getUsername())) {
                response.put("message","El username ya está registrado");
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(response);
            }

            // Validación para el DNI
            if (authService.isDniRegistered((userDTO.getDni()))) {
                response.put("message","Error:El DNI ya está registrado");
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(response);
            }
            // Validación para el email
            if (authService.isEmailRegistered(userDTO.getEmail())) {
                response.put("message","El email ya está registrado");
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(response);
            }

            authService.crearUser(userDTO);
            response.put("message","Usuario registrado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("message","Error al registrar el usuario");
            response.put("value" , e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


    }

    /**
     * endpoint para iniciar sesión
     */
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    /*Retorna un userDTO , donde el username es igual en el DTO y el Entity
     * 1. Encontrar mi userEntity por el username del DTO
     * 2. Una ves que tengo mi Entity sacarle el Id
     * 3. Mandar ese Id al Front
     * */
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO userDTO) {
        try {
            boolean auth = authService.authenticate(userDTO.getUsername(), userDTO.getPassword());
            Map<String, Object> response = new HashMap<>();

            //mandar
            UserEntity usuario = userRepository.findByUsername(userDTO.getUsername());
            long usuarioId = usuario.getId();

            if(auth){

                response.put("message","Logeo exitoso");
                response.put("usuario", usuario);
                response.put("usuarioId", usuarioId);

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
            }else{
                response.put("message","Error en usuario o contraseña" );
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message",  e.getMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

    }

    @GetMapping("/listar")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Map<String, Object>> listarUsuarios(){
        return ResponseEntity.status(HttpStatus.OK).body(authService.listarUsuarios());
    }

} //final
