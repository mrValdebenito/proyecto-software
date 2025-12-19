// Archivo: src/main/java/com/proyecto/IngSoftware/controller/DatoEncuestaController.java

package com.proyecto.IngSoftware.controller;

import com.proyecto.IngSoftware.model.DatoEncuesta;
import com.proyecto.IngSoftware.repository.DatoEncuestaRepository;
import com.proyecto.IngSoftware.repository.ParticipanteRepository;
import com.proyecto.IngSoftware.repository.PreguntaEncuestaRepository;
import com.proyecto.IngSoftware.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/datos-encuesta")
public class DatoEncuestaController {

    @Autowired
    private DatoEncuestaRepository datoEncuestaRepository;
    @Autowired
    private ParticipanteRepository participanteRepository;
    @Autowired
    private PreguntaEncuestaRepository preguntaEncuestaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. CREATE (Ingresar un nuevo dato dicotomizado) - Permitido para Encuestadores
    @PostMapping
    public ResponseEntity<DatoEncuesta> createDatoEncuesta(@RequestBody DatoEncuesta datoEncuestaRequest) {

        // Obtener el usuario autenticado (el Encuestador)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Cargar las entidades relacionadas
        return usuarioRepository.findByUsername(username).map(encuestador -> {

            // Asignar el Encuestador que está logueado
            datoEncuestaRequest.setEncuestador(encuestador);

            // Verificar y asignar Participante (asumiendo que viene solo el ID en el request)
            participanteRepository.findById(datoEncuestaRequest.getParticipante().getIdParticipante()).ifPresent(datoEncuestaRequest::setParticipante);

            // Verificar y asignar Pregunta
            preguntaEncuestaRepository.findById(datoEncuestaRequest.getPregunta().getIdPregunta()).ifPresent(datoEncuestaRequest::setPregunta);

            // Guardar el dato
            DatoEncuesta savedDato = datoEncuestaRepository.save(datoEncuestaRequest);
            return ResponseEntity.ok(savedDato);

        }).orElseGet(() -> ResponseEntity.status(401).build()); // No autorizado si no encuentra el usuario logueado
    }

    // 2. READ ALL / READ by Participante (Solo Administrador puede ver todos los datos)
    @GetMapping
    // Nota: El permiso para este endpoint debe ser 'hasRole("ADMINISTRADOR")' en SecurityConfig
    public List<DatoEncuesta> getAllDatosEncuesta(@RequestParam(required = false) String idParticipante) {
        if (idParticipante != null) {
            return datoEncuestaRepository.findByParticipanteIdParticipante(idParticipante);
        }
        return datoEncuestaRepository.findAll();
    }

    // 3. DELETE (Eliminar un dato) - Solo Administrador
    @DeleteMapping("/{id}")
    // Nota: El permiso para este endpoint debe ser 'hasRole("ADMINISTRADOR")' en SecurityConfig
    public ResponseEntity<Void> deleteDatoEncuesta(@PathVariable Long id) {
        if (datoEncuestaRepository.existsById(id)) {
            datoEncuestaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}