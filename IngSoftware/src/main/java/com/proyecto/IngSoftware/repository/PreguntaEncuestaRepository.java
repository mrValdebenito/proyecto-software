// Archivo: src/main/java/com/proyecto/IngSoftware/repository/PreguntaEncuestaRepository.java

/**
 * Interfaz para acceder a las definiciones de las preguntas de la encuesta.
 */
package com.proyecto.IngSoftware.repository;

import com.proyecto.IngSoftware.model.PreguntaEncuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreguntaEncuestaRepository extends JpaRepository<PreguntaEncuesta, Integer> {
    // Métodos CRUD básicos listos.
}