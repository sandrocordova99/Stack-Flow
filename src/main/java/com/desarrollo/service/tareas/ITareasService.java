package com.desarrollo.service.tareas;

import com.desarrollo.dto.tarea.TareaRequestDTO;
import com.desarrollo.dto.tarea.TareaResponseDTO;
import com.desarrollo.dto.user.TareaDTO;

import java.util.List;
import java.util.Map;

public interface ITareasService {

    boolean existeTareaPorNombre(String nombreTareas);

    TareaResponseDTO crearTarea(TareaRequestDTO tareaDTO, Long userId);

    TareaResponseDTO obtenerTareaPorId(Long id, Long userId);

    List<TareaResponseDTO> listarTareas(Long userId);

    TareaResponseDTO actualizarTarea(Long id, Long userId, TareaRequestDTO tareaDTO);

     void eliminarTarea(Long id, Long userId);
}
