package com.desarrollo.dto.user;

import com.desarrollo.model.ColorEnum;
import com.desarrollo.model.ProyectoEntity;
import jakarta.persistence.*;

import java.util.List;

public class ColorDTO {

    private Long id;

    private String name;

    private long proyectosId;
}
