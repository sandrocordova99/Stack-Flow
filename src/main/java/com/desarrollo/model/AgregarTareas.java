package com.desarrollo.model;

import com.desarrollo.dto.user.TareaDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgregarTareas {
    private Long idProyecto;
    private TareaDTO tarea;
}
