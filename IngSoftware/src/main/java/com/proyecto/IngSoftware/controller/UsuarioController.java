package com.proyecto.IngSoftware.controller;

import com.proyecto.IngSoftware.model.Usuario;
import com.proyecto.IngSoftware.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {

        // Encriptar contraseña antes de guardar
        usuario.setPasswordHash(
                passwordEncoder.encode(usuario.getPasswordHash())
        );

        return usuarioRepository.save(usuario);
    }
}
