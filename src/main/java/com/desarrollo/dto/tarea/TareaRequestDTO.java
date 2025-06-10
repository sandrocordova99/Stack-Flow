package com.desarrollo.dto.tarea;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder

public class TareaRequestDTO {
    private String nombre;
    private Date fechaVencimiento;
    private String prioridad;
    private Long proyectoId;
    private String estado ;
}
