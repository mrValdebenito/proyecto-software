package com.softwareubb.proyecto_software.service;

import com.softwareubb.proyecto_software.model.Role;
import com.softwareubb.proyecto_software.model.RoleName;
import com.softwareubb.proyecto_software.model.User;
import com.softwareubb.proyecto_software.payload.request.SignupRequest;
import com.softwareubb.proyecto_software.repository.RoleRepository;
import com.softwareubb.proyecto_software.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    public void registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Error: ¡El nombre de usuario ya existe!");
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_ENFERMERO).orElseThrow(() -> new RuntimeException("Error: Rol 'LECTOR' por defecto no encontrado."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Rol 'ADMIN' no encontrado."));
                        roles.add(adminRole);
                        break;
                    case "ayudante":
                        Role editorRole = roleRepository.findByName(RoleName.ROLE_AYUDANTE).orElseThrow(() -> new RuntimeException("Error: Rol 'AYUDANTE' no encontrado."));
                        roles.add(editorRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleName.ROLE_ENFERMERO).orElseThrow(() -> new RuntimeException("Error: Rol 'ENFERMERO' no encontrado."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
    }
}