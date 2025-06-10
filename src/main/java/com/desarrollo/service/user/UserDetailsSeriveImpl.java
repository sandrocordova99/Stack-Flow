package com.desarrollo.service.user;

import com.desarrollo.dto.user.UserDTO;
import com.desarrollo.model.RoleEnum;
import com.desarrollo.model.RolesEntity;
import com.desarrollo.model.UserEntity;
import com.desarrollo.repository.userRepository.RoleRepository;
import com.desarrollo.repository.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsSeriveImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.buscarUsuarioPorNombre(username);
                //.orElseThrow(()->new UsernameNotFoundException("El usuario no existe"));

        List<GrantedAuthority> authorityList = new ArrayList<>();

        //obtener roles
        userEntity.getRolesEntitySet().forEach(role-> authorityList
                .add(new SimpleGrantedAuthority("ROLE_".concat(role.getName().name()))));

        //obtener permisos
        userEntity.getRolesEntitySet().stream()
                .flatMap(role->role.getPermisoEntities().stream())
                .forEach(permisos->authorityList.add(new SimpleGrantedAuthority(permisos.getName())));

        return new User(userEntity.getUsername()
        ,userEntity.getPassword()
        ,userEntity.isAccountNoExpired()
        ,userEntity.isEnabled()
        ,userEntity.isCredentialNoExpired()
        ,userEntity.isAccountNoLocked(),authorityList);
    }

    //Método para verificar si un username ya está registrado
    public boolean isUsernameRegistered(String username) {
        return userRepository.existsByUsername(username);
    }

    // Método para verificar si un DNI ya está registrado
    public boolean isDniRegistered(String dni) {
        return userRepository.existsByDni(dni);
    }

    // Método para verificar si un email ya está registrado
    public boolean isEmailRegistered(String email) {
        return userRepository.existsByEmail(email);
    }


    //crear usuario
    public UserEntity crearUser(UserDTO userDTO){
        UserEntity userEntity = UserEntity.builder()
                .username(userDTO.getUsername())
                .password(new BCryptPasswordEncoder().encode(userDTO.getPassword()))
                .nombreCliente(userDTO.getNombreCliente())
                .isEnabled(userDTO.isEnabled())
                .accountNoExpired(userDTO.isAccountNoExpired())
                .accountNoLocked(userDTO.isAccountNoLocked())
                .credentialNoExpired(userDTO.isCredentialNoExpired())
                .dni(userDTO.getDni())
                .email(userDTO.getEmail())
                .rolesEntitySet(
                        userDTO.getRoles().stream()
                        .map(nombreRoles->roleRepository.findByName(RoleEnum.valueOf(nombreRoles))
                                .orElseThrow(()->new RuntimeException("Role no existe")))
                                .collect(Collectors.toSet())
                )
                .build();

     return userRepository.save(userEntity);
    }

    public boolean authenticate(String username, String password) {
        UserEntity userEntity = userRepository.buscarUsuarioPorNombre(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return passwordEncoder.matches(password, userEntity.getPassword());
    }

    public Map<String,Object> listarUsuarios (){

        List<UserEntity> listaUser = userRepository.findAll();
        List<UserDTO> listaUserDTO = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        if(listaUser.isEmpty()){
            response.put("message","No hay usuarios encontrados");
        } else {
            for(UserEntity u : listaUser){

                UserDTO UserDTO = new UserDTO();
                UserDTO.setUsername(u.getUsername());
                UserDTO.setDni(u.getDni());
                UserDTO.setEnabled(u.isEnabled());
                UserDTO.setEmail(u.getEmail());
                UserDTO.setNombreCliente(u.getNombreCliente());
                UserDTO.setUsername(u.getUsername());
                UserDTO.setAccountNoLocked(u.isAccountNoLocked());
                UserDTO.setCredentialNoExpired(u.isCredentialNoExpired());
                UserDTO.setAccountNoExpired(u.isAccountNoExpired());

                Set<RolesEntity> rolesListaEntity =  u.getRolesEntitySet();
                String rolesValor = "";
                Set<String> rolesListaDTO = new HashSet<>();
                for(RolesEntity r : rolesListaEntity ){
                     rolesValor = r.getName().name();
                }
                rolesListaDTO.add(rolesValor);
                UserDTO.setRoles(rolesListaDTO);
                listaUserDTO.add(UserDTO);
            }
            response.put("message","Se encontraron usuarios ");
            response.put("lista",listaUserDTO);
        }

        return response;
    }

}
