package com.proyecto.IngSoftware.controller;

import com.proyecto.IngSoftware.model.Participante;
import com.proyecto.IngSoftware.model.Usuario;
import com.proyecto.IngSoftware.repository.ParticipanteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/participantes") // Ruta base para todos los endpoints
public class ParticipanteController {

    @Autowired
    private ParticipanteRepository participanteRepository;

   // 1. CREATE (Crear un nuevo participante)
    @PostMapping
    public ResponseEntity<?> createParticipante(@RequestBody Participante participante) {
        // Validar si ya existe el RUT
        if (participanteRepository.existsByRut(participante.getRut())) {
            // Retornar un error 409 Conflict con un mensaje claro
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Error: El RUT ingresado (" + participante.getRut() + ") ya existe en el sistema.");
        }

        // Si no existe, lo guarda
        Participante nuevoParticipante = participanteRepository.save(participante);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoParticipante);
    }

    // 2. READ ALL (Obtener todos los participantes)
    @GetMapping
    public List<Participante> listar(HttpSession session) {
        // Leer la misma clave que usaste al iniciar sesión
        Usuario usuario = (Usuario) session.getAttribute("usuarioActual"); // ✅

        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Debes iniciar sesión");
        }

        if (!usuario.getRol().equalsIgnoreCase("Administrador")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso solo para administradores");
        }

        return participanteRepository.findAll();
    }



    // 3. READ ONE (Obtener un participante por ID)
    @GetMapping("/{id}")
    public ResponseEntity<Participante> getParticipanteById(@PathVariable String id) {
        Optional<Participante> participante = participanteRepository.findById(id);

        if (participante.isPresent()) {
            return ResponseEntity.ok(participante.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. UPDATE (Actualizar un participante existente)
    @PutMapping("/{id}")
    public ResponseEntity<Participante> updateParticipante(@PathVariable String id, @RequestBody Participante detallesParticipante) {
        Optional<Participante> participanteOptional = participanteRepository.findById(id);

        if (participanteOptional.isPresent()) {
            Participante participante = participanteOptional.get();
            // Actualizar solo los campos que pueden cambiar
            participante.setNombre(detallesParticipante.getNombre());
            participante.setPeso(detallesParticipante.getPeso());
            participante.setEmail(detallesParticipante.getEmail());
            // No se debería permitir cambiar idParticipante o rut aquí, pero depende de tu lógica de negocio

            final Participante updatedParticipante = participanteRepository.save(participante);
            return ResponseEntity.ok(updatedParticipante);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE (Eliminar un participante)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipante(@PathVariable String id) {
        if (participanteRepository.existsById(id)) {
            participanteRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}