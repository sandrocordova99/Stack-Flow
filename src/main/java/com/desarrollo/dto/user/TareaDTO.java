package com.desarrollo.dto.user;

import com.desarrollo.model.EstadoEnum;
import com.desarrollo.model.PrioridadEnum;
import com.desarrollo.model.ProyectoEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TareaDTO {

    private Long id;

    private String nombre;

    private Date fechaVencimiento;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private PrioridadEnum prioridad;

    private long proyectoId;

    private String estado ;
}
