package com.softwareubb.proyecto_software.controller;

import com.softwareubb.proyecto_software.model.Paciente;
import com.softwareubb.proyecto_software.service.DataExportService;
import com.softwareubb.proyecto_software.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;
    
    @Autowired
    private DataExportService dataExportService;

    @GetMapping
    @PreAuthorize("hasRole('LECTOR') or hasRole('EDITOR') or hasRole('ADMIN')")
    public Iterable<Paciente> getAllPacientes() {
        return pacienteService.obtenerTodosLosPacientes();
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('LECTOR') or hasRole('EDITOR') or hasRole('ADMIN')")
    public Paciente getPacienteById(@PathVariable Long id) {
        return pacienteService.obtenerPacientePorId(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('EDITOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createPaciente(@RequestBody Paciente paciente) {
        
        try {
            Paciente nuevoPaciente = pacienteService.crearPaciente(paciente);
            return ResponseEntity.ok(nuevoPaciente);       
        } catch (RuntimeException e) {
            
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EDITOR') or hasRole('ADMIN')")
    public Paciente updatePaciente(@PathVariable Long id, @RequestBody Paciente pacienteDetalles) {
        return pacienteService.actualizarPaciente(id, pacienteDetalles);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePaciente(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok("Paciente eliminado exitosamente");
    }

    @GetMapping("/export")
    @PreAuthorize("hasRole('EDITOR') or hasRole('ADMIN')")
    public ResponseEntity<String> exportPacientes(
            @RequestParam(defaultValue = "false") boolean dicotomizar) {
        
        String csvData = dataExportService.generateCSVExport(dicotomizar);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pacientes.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=utf-8");

        return ResponseEntity.ok().headers(headers).body(csvData);
    }
}