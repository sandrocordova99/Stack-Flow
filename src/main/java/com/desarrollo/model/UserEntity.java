package com.desarrollo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String username;

    private String password;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "account_no_expired")
    private boolean accountNoExpired;

    @Column(name = "account_no_locked")
    private boolean accountNoLocked; //

    @Column(name = "credential_no_expired")
    private boolean credentialNoExpired; //

    @Column(name = "nombre_cliente",nullable = false)
    private String nombreCliente;

    @Column(name = "dni",nullable = false,length = 8,unique = true)
    private String dni;

    @Column(name = "email",nullable = false,length = 100,unique = true)
    private String email;


    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
            @JoinTable(name = "user_roles",
                    joinColumns = @JoinColumn(name = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolesEntity> rolesEntitySet = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "usuarios")
    private Set<ProyectoEntity> proyectos = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "tarea_usuario",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_tarea")
    )
    private Set<TareaEntity> tareas = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }



}
