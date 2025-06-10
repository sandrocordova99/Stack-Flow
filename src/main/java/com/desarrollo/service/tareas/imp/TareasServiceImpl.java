package com.desarrollo.service.tareas.imp;

import com.desarrollo.dto.tarea.TareaRequestDTO;
import com.desarrollo.dto.tarea.TareaResponseDTO;

import com.desarrollo.model.*;
import com.desarrollo.repository.ProyectoRepository;
import com.desarrollo.repository.tareaRepository.TareaRespository;
import com.desarrollo.repository.userRepository.UserRepository;
import com.desarrollo.service.tareas.ITareasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class TareasServiceImpl implements ITareasService {

    private final TareaRespository tareaRepository;
    private final ProyectoRepository proyectoRepository;
    private final UserRepository userRepository;

    @Override
    public boolean existeTareaPorNombre(String nombreTarea) {
        if (nombreTarea == null || nombreTarea.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la tarea no puede ser nulo o vacío.");
        }

        return tareaRepository.findByNombre(nombreTarea).isPresent();
    }

    @Override
    public TareaResponseDTO crearTarea(TareaRequestDTO tareaDTO, Long userId) {
        // Verificamos si el usuario existe
        UserEntity usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + userId));


        //validar q el proyecto este viculado al suario
        ProyectoEntity proyecto = proyectoRepository.findById(tareaDTO.getProyectoId())
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + tareaDTO.getProyectoId()));

        //validamos si el usuario es parte del proyecto
        if (!proyecto.getUsuarios().contains(usuario)) {
            throw new RuntimeException("El usuario no tiene acceso a este proyecto.");
        }

        // crear la tarea
        if(existeTareaPorNombre(tareaDTO.getNombre())){
            throw new RuntimeException(String.format("La tarea %s ya existe, use otro nombre",tareaDTO.getNombre()));
        }else{
            TareaEntity tarea = new TareaEntity();
            tarea.setNombre(tareaDTO.getNombre());
            tarea.setFechaVencimiento(tareaDTO.getFechaVencimiento());
            tarea.setPrioridad(PrioridadEnum.valueOf(tareaDTO.getPrioridad()));
            tarea.setProyectos(proyecto);
            tarea.setEstadoEnum(EstadoEnum.valueOf((tareaDTO.getEstado())));
            TareaEntity tareaGuardada = tareaRepository.save(tarea);
            return mapToResponseDTO(tareaGuardada);
        }



    }

    // Listar todas las tareas asociadas a los proyectos del usuario
    @Override
    public List<TareaResponseDTO> listarTareas(Long userId) {

        UserEntity usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + userId));

        // Obtenemos los proyectos del usuario
        Set<ProyectoEntity> proyectosDelUsuario = usuario.getProyectos();

        // Buscamos las tareas que pertenecen a los proyectos del usuario
        List<TareaEntity> tareas = tareaRepository.findByProyectosIn(proyectosDelUsuario);


        return tareas.stream().map(this::mapToResponseDTO).toList();
    }


    @Override
    public TareaResponseDTO obtenerTareaPorId(Long id, Long userId) {

        UserEntity usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + userId));

        // Obtenemos la tarea
        TareaEntity tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));

        // validamos si la tarea pertenece a un proyecto del usuario
        if (!usuario.getProyectos().contains(tarea.getProyectos())) {
            throw new RuntimeException("La tarea no pertenece a un proyecto del usuario.");
        }

        return mapToResponseDTO(tarea);
    }


    @Override
    public TareaResponseDTO actualizarTarea(Long id, Long userId, TareaRequestDTO tareaDTO) {

        UserEntity usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + userId));

        // filtramos la tarea existente
        TareaEntity tareaExistente = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));

        // validamos si la tarea pertenece a un proyecto del usuario
        if (!usuario.getProyectos().contains(tareaExistente.getProyectos())) {
            throw new RuntimeException("La tarea no pertenece a un proyecto del usuario.");
        }

        // update de  la tarea
        tareaExistente.setNombre(tareaDTO.getNombre());
        tareaExistente.setFechaVencimiento(tareaDTO.getFechaVencimiento());


        tareaExistente.setPrioridad(PrioridadEnum.valueOf(tareaDTO.getPrioridad()));
        tareaExistente.setEstadoEnum(EstadoEnum.valueOf(tareaDTO.getEstado()));


        // si el proyecto también cambia, verificamos si es uno del usuario
        ProyectoEntity nuevoProyecto = proyectoRepository.findById(tareaDTO.getProyectoId())
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + tareaDTO.getProyectoId())); 
        
        

        /*// validamos si el proyecto es del usuario
        if (!nuevoProyecto.getUsuarios().contains(usuario)) {
            throw new RuntimeException("El usuario no tiene acceso a este proyecto.");
        }*/

        tareaExistente.setProyectos(nuevoProyecto);

        // y si todo es ok actualizamos
        TareaEntity tareaActualizada = tareaRepository.save(tareaExistente);
        return mapToResponseDTO(tareaActualizada);
    }


    @Override
    public void eliminarTarea(Long id, Long userId) {

        UserEntity usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + userId));

        // obtenemos la tarea
        TareaEntity tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));

        // verificamos si la tarea pertenece a un proyecto del usuario
        if (!usuario.getProyectos().contains(tarea.getProyectos())) {
            throw new RuntimeException("La tarea no pertenece a un proyecto del usuario.");
        }

        // si todo es ok eliminamos
        tareaRepository.delete(tarea);
    }

    private TareaResponseDTO mapToResponseDTO(TareaEntity tarea) {
      return   TareaResponseDTO.builder()
                .id(tarea.getId())
                .nombre(tarea.getNombre())
                .fechaVencimiento(tarea.getFechaVencimiento())
                .prioridad(tarea.getPrioridad().toString())
                .proyectoNombre(tarea.getProyectos().getNombre())
                .build();
    }


}
