package com.desarrollo.controller;

import com.desarrollo.dto.tarea.TareaRequestDTO;
import com.desarrollo.dto.tarea.TareaResponseDTO;
import com.desarrollo.model.UserEntity;
import com.desarrollo.service.tareas.ITareasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TareaController {

    private final ITareasService tareaService;

    @PostMapping("/crearTarea")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> crearTarea(@RequestBody TareaRequestDTO tareaDTO,
                                                         @RequestParam Long userId) {

        TareaResponseDTO tareaCreada = tareaService.crearTarea(tareaDTO, userId);
        Map<String,Object> respuesta = new HashMap<>();

        respuesta.put("tarea" , tareaCreada);
        respuesta.put("message" , "Tarea creada con exito");
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }


    @GetMapping("/listarTareas")
   /* @PreAuthorize("hasRole('ADMIN')")*/
    public ResponseEntity<Map<String,Object>> listarTareas(@RequestParam Long userId) {
        List<TareaResponseDTO> tareas = tareaService.listarTareas(userId);
        Map<String,Object> respuesta = new HashMap<>();
        respuesta.put("tarea" , tareas);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }


    @GetMapping("/obtenerTarea/{id}")
 /*   @PreAuthorize("hasRole('ADMIN')")*/
    public ResponseEntity<Map<String,Object>> obtenerTareaPorId(@PathVariable Long id,
                                                              @RequestParam Long userId) {
        TareaResponseDTO tarea = tareaService.obtenerTareaPorId(id,userId);
        Map<String,Object> respuesta = new HashMap<>();
        respuesta.put("tarea" , tarea);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }


    @PutMapping("/actualizarTarea/{id}")
   /* @PreAuthorize("hasRole('ADMIN')")*/
    public ResponseEntity<Map<String,Object>> actualizarTarea(@PathVariable Long id,
                                                            @RequestBody TareaRequestDTO tareaDTO,
                                                            @RequestParam Long userId) {
        TareaResponseDTO tareaActualizada = tareaService.actualizarTarea(id, userId, tareaDTO);
        Map<String,Object> respuesta = new HashMap<>();
        respuesta.put("tarea" , tareaActualizada);
        respuesta.put("message" , "tarea agregada con exito");
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }


    @DeleteMapping("/borrarTarea/{id}")
    /*@PreAuthorize("hasRole('ADMIN')")*/
    public ResponseEntity<Map<String,Object>> eliminarTarea(@PathVariable Long id,
                                                            @RequestParam Long userId) {
        tareaService.eliminarTarea(id, userId);
        Map<String,Object> respuesta = new HashMap<>();
        respuesta.put("message" , "tarea con el id: " + id + " se elimino con exito");
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}

