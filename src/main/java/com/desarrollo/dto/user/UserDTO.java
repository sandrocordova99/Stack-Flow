package com.desarrollo.dto.user;

import jakarta.persistence.Column;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String username;
    private String password;

    private String nombreCliente;
    private String dni;
    private String email;


    /*----null---*/
    private Set<String> roles;
    
    

    private boolean isEnabled;
    private boolean accountNoExpired;
    private boolean accountNoLocked;
    private boolean credentialNoExpired;



}
