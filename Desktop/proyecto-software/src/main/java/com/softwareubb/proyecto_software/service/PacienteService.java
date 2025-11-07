package com.softwareubb.proyecto_software.service;

import com.softwareubb.proyecto_software.model.Paciente;
import com.softwareubb.proyecto_software.repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Paciente> obtenerTodosLosPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente obtenerPacientePorId(Long id) {
        return pacienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + id));
    }

    public Paciente crearPaciente(Paciente paciente) {
        pacienteRepository.findByRut(paciente.getRut()).ifPresent(p -> {
            throw new RuntimeException("Error: El RUT ya está registrado");
        });
        return pacienteRepository.save(paciente);
    }

    public Paciente actualizarPaciente(Long id, Paciente pacienteDetalles) {
        Paciente pacienteExistente = obtenerPacientePorId(id);

        if (!pacienteExistente.getRut().equals(pacienteDetalles.getRut())) {
            pacienteRepository.findByRut(pacienteDetalles.getRut()).ifPresent(p -> {
                throw new RuntimeException("Error: El nuevo RUT ya pertenece a otro paciente");
            });
        }
        
        pacienteExistente.setNombre(pacienteDetalles.getNombre());
        pacienteExistente.setApellido(pacienteDetalles.getApellido());
        pacienteExistente.setRut(pacienteDetalles.getRut());
        pacienteExistente.setDatosFormularioJson(pacienteDetalles.getDatosFormularioJson());

        return pacienteRepository.save(pacienteExistente);
    }

    public void eliminarPaciente(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Paciente no encontrado con ID: " + id);
        }
        pacienteRepository.deleteById(id);
    }
}