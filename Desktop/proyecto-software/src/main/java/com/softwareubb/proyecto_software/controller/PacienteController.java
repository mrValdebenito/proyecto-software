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

<<<<<<< HEAD
=======
    @Autowired
    private DataExportService dataExportService;

>>>>>>> 31417c53bed333b51d2c19a10bc759766a432675
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public Paciente createPaciente(@Valid @RequestBody Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'LECTOR')")
    public Iterable<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }
<<<<<<< HEAD
    //HU 17: El cliente necesita crear, modificar, leer y eliminar la información de los pacientes
=======

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Paciente> updatePaciente(@PathVariable Long id, @RequestBody Paciente detallesPaciente) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    paciente.setNombre(detallesPaciente.getNombre());
                    paciente.setRut(detallesPaciente.getRut());
                    paciente.setDatosFormularioJson(detallesPaciente.getDatosFormularioJson());
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
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
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
>>>>>>> 31417c53bed333b51d2c19a10bc759766a432675
}