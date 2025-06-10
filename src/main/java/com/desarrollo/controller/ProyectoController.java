package com.desarrollo.controller;

import com.desarrollo.dto.proyecto.ProyectoRequestDTO;
import com.desarrollo.dto.proyecto.ProyectoResponseDTO;
import com.desarrollo.model.UserEntity;
import com.desarrollo.repository.ProyectoRepository;
import com.desarrollo.service.Proyecto.ProyectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api/v1/proyecto")
@RequiredArgsConstructor
public class ProyectoController {
    private final ProyectoService proyectoService;
    private final ProyectoRepository proyectoRepository;



    // Crear un proyecto
    @PostMapping("/crearProyecto")
    public ResponseEntity<Map<String,Object>> crearProyecto(@RequestBody ProyectoRequestDTO proyectoDTO,
                                                            @RequestParam Long userId) {

        Map<String,Object> respuesta = new HashMap<>();

        //validar
        if(proyectoRepository.existsByNombre(proyectoDTO.getNombre())){
            respuesta.put("message" , "Proyecto de nombre " + proyectoDTO.getNombre() +" ya existe");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(respuesta);
        } else {
            ProyectoResponseDTO proyectoCreado = proyectoService.crearProyecto(proyectoDTO, userId);

            respuesta.put("proyecto" , proyectoCreado);
            respuesta.put("message" , "Proyecto " + proyectoCreado.getNombre() + " Creado con exito :)");
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }
    }


    @GetMapping("/obtenerPorId/{proyectoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> obtenerProyectoPorId(@PathVariable Long proyectoId,
                                                                   @RequestParam Long userId) {
        ProyectoResponseDTO proyecto = proyectoService.obtenerProyectoPorId(proyectoId, userId);
        Map<String,Object> respuesta = new HashMap<>();
        respuesta.put("proyecto" , proyecto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }



    // lsitar todos los proyectos de un usuario , MAS IMPORTANTE
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Map<String,Object>> listarProyectos(@PathVariable Long usuarioId) {
        List<ProyectoResponseDTO> proyectos = proyectoService.listarProyectos(usuarioId);
        Map<String,Object> respuesta = new HashMap<>();
        respuesta.put("proyecto" , proyectos);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @PutMapping("/actualizarProyecto/{proyectoId}")
    public ResponseEntity<Map<String, Object>> actualizarProyecto(@PathVariable Long proyectoId, @RequestParam Long userId,
                                                                  @RequestBody ProyectoRequestDTO proyectoDTO) {

        ProyectoResponseDTO proyectoActualizado = proyectoService.actualizarProyecto(proyectoId, userId, proyectoDTO);
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("proyecto", proyectoActualizado);
        respuesta.put("message", "Proyecto Actualizado");
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }


    @DeleteMapping("/eliminarProyecto/{proyectoId}")
    public ResponseEntity<Map<String, Object>> eliminarProyecto(@PathVariable Long proyectoId, @RequestParam Long userId) {
        proyectoService.eliminarProyecto(proyectoId, userId); Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("message", "Proyecto eliminado con el id : " + proyectoId);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }


}






