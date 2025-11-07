package com.softwareubb.proyecto_software.controller;

import com.softwareubb.proyecto_software.model.Formulario;
import com.softwareubb.proyecto_software.model.Pregunta;
import com.softwareubb.proyecto_software.repository.FormularioRepository;
import com.softwareubb.proyecto_software.repository.PreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/formularios")
public class FormularioController {

    @Autowired
    private FormularioRepository formularioRepository;

    @Autowired
    private PreguntaRepository preguntaRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Formulario crearFormulario(@RequestBody Formulario formulario) {
        return formularioRepository.save(formulario);
    }

    @PostMapping("/{idFormulario}/preguntas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Pregunta> agregarPreguntaAFormulario(
            @PathVariable Long idFormulario,
            @RequestBody Pregunta pregunta) {
        
        Optional<Formulario> formularioOpt = formularioRepository.findById(idFormulario);
        
        if (formularioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        pregunta.setFormulario(formularioOpt.get());
        Pregunta preguntaGuardada = preguntaRepository.save(pregunta);
        
        return ResponseEntity.ok(preguntaGuardada);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'LECTOR')")
    public Iterable<Formulario> obtenerTodosLosFormularios() {
        return formularioRepository.findAll();
    }
}