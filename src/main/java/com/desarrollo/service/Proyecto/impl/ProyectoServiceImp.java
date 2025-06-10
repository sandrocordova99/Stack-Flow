package com.desarrollo.service.Proyecto.impl;

import com.desarrollo.dto.proyecto.ProyectoRequestDTO;
import com.desarrollo.dto.proyecto.ProyectoResponseDTO;
import com.desarrollo.dto.user.UsuarioDTO;
import com.desarrollo.exception.ResourceNotFoundException;
import com.desarrollo.exception.UnauthorizedAccessException;
import com.desarrollo.model.ColorEntity;
import com.desarrollo.model.ProyectoEntity;
import com.desarrollo.model.UserEntity;
import com.desarrollo.repository.ColorRepository;
import com.desarrollo.repository.ProyectoRepository;
import com.desarrollo.repository.userRepository.UserRepository;
import com.desarrollo.service.Proyecto.ProyectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProyectoServiceImp implements ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final ColorRepository colorRepository;
    private final UserRepository userRepository;


    @Override
    public ProyectoResponseDTO crearProyecto(ProyectoRequestDTO proyectoDTO, Long userid) {
        //buscar si el usuario existe
        UserEntity usuario = userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + userid));

        ProyectoEntity proyecto = new ProyectoEntity();
        proyecto.setNombre(proyectoDTO.getNombre());
        //asignamos el usuario que creó el proyecto
        proyecto.setUsuarios(new HashSet<>(Collections.singletonList(usuario)));

        Optional<ColorEntity> color = colorRepository.findById(proyectoDTO.getColorId());
        proyecto.setColores(color.get());
        ProyectoEntity proyectoGuardado = proyectoRepository.save(proyecto);

        return mapToResponse(proyectoGuardado);
    }

    @Override
    public ProyectoResponseDTO obtenerProyectoPorId(Long proyectoId, Long usuarioId) {
        ProyectoEntity proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow();

        // validar si el proyecto pertenece al usuario autenticado
        if (!proyecto.getUsuarios().stream().anyMatch(usuario -> usuario.getId().equals(usuarioId))) {
            throw new UnauthorizedAccessException("No tienes permiso para acceder a este proyecto");
        }

        return mapToResponse(proyecto);
    }

    @Override
    public List<ProyectoResponseDTO> listarProyectos(Long usuarioId) {
        UserEntity usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));

        List<ProyectoEntity> proyectos = proyectoRepository.findByUsuarios_Id(usuarioId);
        return proyectos.stream().map(this::mapToResponse).toList();
    }

    @Override
    public ProyectoResponseDTO actualizarProyecto(Long proyectoId, Long usuarioId, ProyectoRequestDTO /*nuevo proyecto*/proyectoDTO) {
        ProyectoEntity proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow();

        // validar si el proyecto pertenece al usuario autenticado
        if (!proyecto.getUsuarios().stream().anyMatch(usuario -> usuario.getId().equals(usuarioId))) {
            throw new UnauthorizedAccessException("No tienes permiso para actualizar este proyecto");
        }

        proyecto.setNombre(proyectoDTO.getNombre());
        Optional<ColorEntity> color = colorRepository.findById(proyectoDTO.getColorId());
        proyecto.setColores(color.get());

        ProyectoEntity proyectoActualizado = proyectoRepository.save(proyecto);

        return mapToResponse(proyectoActualizado);
    }

    @Override
    public List<ProyectoResponseDTO> obtenerProyectosPorUsuario(Long userId) {
        UserEntity usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + userId));

        // aqui filtramos los proyectos donde el usuario es uno de los creadores
        Set<ProyectoEntity> proyectos = usuario.getProyectos();

        return proyectos.stream().map(this::mapToResponse).toList();
    }

    @Override
    public void eliminarProyecto(Long proyectoId, Long usuarioId) {
        ProyectoEntity proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow();

        // validar si el proyecto pertenece al usuario autenticado
        if (!proyecto.getUsuarios().stream().anyMatch(usuario -> usuario.getId().equals(usuarioId))) {
            throw new UnauthorizedAccessException("No tienes permiso para eliminar este proyecto");
        }

        proyectoRepository.delete(proyecto);
    }

    //maper manual

    private  ProyectoResponseDTO mapToResponse(ProyectoEntity proyecto){
        return ProyectoResponseDTO.builder()
                .id(proyecto.getId())
                .nombre(proyecto.getNombre())
                .color(proyecto.getColores() != null ? proyecto.getColores().getName().toString() : null)
                .usuarios(proyecto.getUsuarios().stream()
                                .map(usuario->{
                                    return UsuarioDTO.builder()
                                            .id(usuario.getId())
                                            .nombre(usuario.getNombreCliente())
                                            .email(usuario.getEmail())
                                            .build();
                                }).collect(Collectors.toSet())
                        )
                .build();
    }
}

