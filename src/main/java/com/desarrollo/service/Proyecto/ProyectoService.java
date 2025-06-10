package com.desarrollo.service.Proyecto;

import com.desarrollo.dto.proyecto.ProyectoRequestDTO;
import com.desarrollo.dto.proyecto.ProyectoResponseDTO;

import java.util.List;

public interface ProyectoService {


    ProyectoResponseDTO crearProyecto(ProyectoRequestDTO proyectoDTO,Long userId);
    ProyectoResponseDTO obtenerProyectoPorId(Long proyectoId, Long usuarioId);
    List<ProyectoResponseDTO> listarProyectos(Long usuarioId);
    ProyectoResponseDTO actualizarProyecto(Long proyectoId, Long usuarioId, ProyectoRequestDTO proyectoDTO);
    void eliminarProyecto(Long proyectoId, Long usuarioId);
    List<ProyectoResponseDTO> obtenerProyectosPorUsuario(Long userId);





}
