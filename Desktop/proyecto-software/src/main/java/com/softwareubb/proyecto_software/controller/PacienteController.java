package com.softwareubb.proyecto_software.controller;

import com.softwareubb.proyecto_software.model.Paciente;
import com.softwareubb.proyecto_software.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    // Ruta para CREAR (HU #7)
    @PostMapping
    public Paciente createPaciente(@RequestBody Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    // Ruta para LEER todos (HU #7)
    @GetMapping
    public Iterable<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    // Aquí irían las rutas PUT y DELETE (HU #7)
    // ...
}