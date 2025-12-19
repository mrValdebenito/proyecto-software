package com.proyecto.IngSoftware.controller;

import com.proyecto.IngSoftware.model.PreguntaEncuesta;
import com.proyecto.IngSoftware.model.Usuario;
import com.proyecto.IngSoftware.repository.PreguntaEncuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/preguntas-encuesta")
public class PreguntaEncuestaController {

    @Autowired
    private PreguntaEncuestaRepository preguntaRepository;

    // --- Listar preguntas ---
    @GetMapping
    public List<PreguntaEncuesta> getAll(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioActual");

        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Debes iniciar sesión");
        }

        if (!usuario.getRol().equalsIgnoreCase("Administrador")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso solo para administradores");
        }

        return preguntaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreguntaEncuesta> getById(@PathVariable Integer id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioActual");

        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Debes iniciar sesión");
        }

        if (!usuario.getRol().equalsIgnoreCase("Administrador")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso solo para administradores");
        }

        Optional<PreguntaEncuesta> pregunta = preguntaRepository.findById(id);
        return pregunta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- Crear / Editar / Eliminar preguntas ---
    // Aquí puedes permitir que solo los administradores puedan modificar
    @PostMapping
    public PreguntaEncuesta create(@RequestBody PreguntaEncuesta pregunta, HttpSession session) {
        verificarAdmin(session);
        return preguntaRepository.save(pregunta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PreguntaEncuesta> update(@PathVariable Integer id,
                                                   @RequestBody PreguntaEncuesta detalles,
                                                   HttpSession session) {
        verificarAdmin(session);

        return preguntaRepository.findById(id).map(p -> {
            p.setEnunciado(detalles.getEnunciado());
            p.setCategoria(detalles.getCategoria());
            return ResponseEntity.ok(preguntaRepository.save(p));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id, HttpSession session) {
        verificarAdmin(session);

        if (preguntaRepository.existsById(id)) {
            preguntaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // --- Método helper para verificar admin ---
    private void verificarAdmin(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioActual");
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Debes iniciar sesión");
        }
        if (!usuario.getRol().equalsIgnoreCase("Administrador")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso solo para administradores");
        }
    }
}
