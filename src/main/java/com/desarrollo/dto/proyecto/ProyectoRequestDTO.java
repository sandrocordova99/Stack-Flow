package com.desarrollo.dto.proyecto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoRequestDTO {

    private String nombre;
    private long colorId;
    /*jalar un usuario el que lo crea*/
    private Set<Long> usuarioIds;



}
