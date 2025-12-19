package com.softwareubb.proyecto_software.controller;

import com.softwareubb.proyecto_software.model.Paciente;
import com.softwareubb.proyecto_software.repository.PacienteRepository;
import com.softwareubb.proyecto_software.service.DataExportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private DataExportService dataExportService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AYUDANTE')")
    public Paciente createPaciente(@Valid @RequestBody Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AYUDANTE', 'ENFERMERO')")
    public Iterable<Paciente> getAllPacientes(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer edad) {
        
        if (edad != null) {
            return pacienteRepository.findByEdad(edad);
        } else if (search != null && !search.isEmpty()) {
            return pacienteRepository.findByRutContainingOrNombreContaining(search, search);
        } else {
            return pacienteRepository.findAll();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AYUDANTE', 'ENFERMERO')")
    public ResponseEntity<Paciente> getPacienteById(@PathVariable Long id) {
        return pacienteRepository.findById(id)
                .map(paciente -> ResponseEntity.ok(paciente))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AYUDANTE')")
    public ResponseEntity<Paciente> updatePaciente(@PathVariable Long id, @Valid @RequestBody Paciente detallesPaciente) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    paciente.setNombre(detallesPaciente.getNombre());
                    paciente.setRut(detallesPaciente.getRut());
                    paciente.setDatosFormularioJson(detallesPaciente.getDatosFormularioJson());
                    paciente.setTieneCancer(detallesPaciente.getTieneCancer());
                    paciente.setEdad(detallesPaciente.getEdad());
                    Paciente updatedPaciente = pacienteRepository.save(paciente);
                    return ResponseEntity.ok(updatedPaciente);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePaciente(@PathVariable Long id) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    pacienteRepository.delete(paciente);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/exportar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<byte[]> exportarPacientesCSV(@RequestParam(defaultValue = "false") boolean dicotomizar) {
        String csvContent = dataExportService.generateCSVExport(dicotomizar);
        byte[] csvBytes = csvContent.getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        String filename = "pacientes_export_" + (dicotomizar ? "dicotomizado" : "completo") + ".csv";
        headers.setContentDispositionFormData("attachment", filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }
}