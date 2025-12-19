package com.proyecto.IngSoftware.controller;

import com.proyecto.IngSoftware.model.Participante;
import com.proyecto.IngSoftware.model.Usuario;
import com.proyecto.IngSoftware.repository.ParticipanteRepository;
import com.proyecto.IngSoftware.service.ExportService; 
import jakarta.servlet.http.HttpServletResponse;       
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException; 
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private ExportService exportService; 

    // 1. CREATE (Mantiene tu validación de RUT y @Valid)
    @PostMapping
    public ResponseEntity<?> createParticipante(@Valid @RequestBody Participante participante) {
        if (participanteRepository.existsByRut(participante.getRut())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Error: El RUT ingresado (" + participante.getRut() + ") ya existe en el sistema.");
        }
        Participante nuevoParticipante = participanteRepository.save(participante);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoParticipante);
    }

    // 2. READ ALL (Mantiene tu búsqueda por nombre)
    @GetMapping
    public List<Participante> listar(HttpSession session, @RequestParam(required = false) String busqueda) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioActual");
        if (usuario == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Debes iniciar sesión");
        if (!usuario.getRol().equalsIgnoreCase("Administrador")) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso solo para administradores");

        if (busqueda != null && !busqueda.isEmpty()) {
            return participanteRepository.findByNombreContainingIgnoreCase(busqueda);
        }
        return participanteRepository.findAll();
    }

    // 3. READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Participante> getParticipanteById(@PathVariable String id) {
        Optional<Participante> participante = participanteRepository.findById(id);
        return participante.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- NUEVO ENDPOINT: EXPORTAR A CSV 
    @GetMapping("/exportar")
    public void exportarCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"participantes.csv\"");
        exportService.generarCsv(response.getWriter(), participanteRepository.findAll());
    }

    // 4. UPDATE (Mantiene validación)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateParticipante(@PathVariable String id, @Valid @RequestBody Participante detallesParticipante) {
        Optional<Participante> participanteOptional = participanteRepository.findById(id);

        if (participanteOptional.isPresent()) {
            Participante participante = participanteOptional.get();
            participante.setNombre(detallesParticipante.getNombre());
            participante.setPeso(detallesParticipante.getPeso());
            participante.setEmail(detallesParticipante.getEmail());
            
            final Participante updatedParticipante = participanteRepository.save(participante);
            return ResponseEntity.ok(updatedParticipante);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipante(@PathVariable String id) {
        if (participanteRepository.existsById(id)) {
            participanteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}