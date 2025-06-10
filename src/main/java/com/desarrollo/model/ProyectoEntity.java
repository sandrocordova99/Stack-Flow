package com.desarrollo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proyecto")
public class ProyectoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto", unique = true)
    private Long id;

    @Column(name ="nombre",nullable = false , length = 100 )
    private String nombre;

    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "proyecto_id"),
            inverseJoinColumns = @JoinColumn(name = "usuarios_id")
    )
    private Set<UserEntity> usuarios =  new HashSet<>();

    @OneToMany(mappedBy = "proyectos")
    private Set<TareaEntity> tareas =  new HashSet<>();

    @ManyToOne()
    @JoinColumn(
            name = "colores"
    )
    private ColorEntity colores;
}
