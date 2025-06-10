package com.desarrollo.dto.tarea;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Builder
@Getter
@Setter

public class TareaResponseDTO {
    private Long id;
    private String nombre;
    private Date fechaVencimiento;
    private String prioridad;
    private String proyectoNombre;
    //estado
}
