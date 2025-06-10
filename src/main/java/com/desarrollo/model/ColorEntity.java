package com.desarrollo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "color")
public class ColorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_color", unique = true)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private ColorEnum name;

    @OneToMany(mappedBy = "colores")
    @JsonIgnore
    private List<ProyectoEntity> proyectos = new ArrayList<>();
}
