package com.desarrollo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tarea")
    public class TareaEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_tarea", unique = true)
        private Long id;

        @Column(name = "nombre" , length = 100 , nullable = false)
        private String nombre;

        @DateTimeFormat(pattern = "yyyy/MM/dd")
        private Date fechaVencimiento;

        @Enumerated(EnumType.STRING)
        private PrioridadEnum prioridad;

         @JsonIgnore
        @ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
        @JoinColumn(
                name = "proyectos"
        )
        private ProyectoEntity proyectos = new ProyectoEntity();

        @Enumerated(EnumType.STRING)
        private EstadoEnum estadoEnum ;

    }
