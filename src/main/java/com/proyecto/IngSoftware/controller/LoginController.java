package com.proyecto.IngSoftware.controller;

import com.proyecto.IngSoftware.model.Usuario;
import com.proyecto.IngSoftware.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Mostrar formulario de login (opcional, si quieres servirlo desde Spring)
    @GetMapping("/login")
    public String loginForm() {
        return "login"; // nombre del HTML sin .html si usas Thymeleaf, o sirve tu static html
    }

    // Recibe datos del form clásico
    @PostMapping("/login")
    @ResponseBody
    public Map<String,Object> login(@RequestBody Map<String,String> body, HttpSession session) {
        String username = body.get("username");
        String password = body.get("password");

        Map<String,Object> respuesta = new HashMap<>();
        Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);
        if(optionalUsuario.isEmpty()) {
            respuesta.put("error","Usuario o contraseña incorrectos");
            return respuesta;
        }

        Usuario usuario = optionalUsuario.get();
        if(!BCrypt.checkpw(password, usuario.getPasswordHash())) {
            respuesta.put("error","Usuario o contraseña incorrectos");
            return respuesta;
        }

        session.setAttribute("usuarioActual", usuario);

        respuesta.put("id", usuario.getIdUsuario());
        respuesta.put("nombre", usuario.getNombreUsuario());
        respuesta.put("rol", usuario.getRol());
        respuesta.put("username", usuario.getUsername());
        return respuesta;
    }

}
