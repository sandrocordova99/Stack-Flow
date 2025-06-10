package com.desarrollo.dto.proyecto;

import com.desarrollo.dto.user.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoResponseDTO {
    private Long id;
    private String nombre;
    private String color;
    private Set<UsuarioDTO> usuarios;
}
