// Archivo: src/main/java/com/proyecto/IngSoftware/repository/DatoEncuestaRepository.java

package com.proyecto.IngSoftware.repository;

import com.proyecto.IngSoftware.model.DatoEncuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatoEncuestaRepository extends JpaRepository<DatoEncuesta, Long> {
    // Método útil: buscar todas las respuestas de un participante específico
    List<DatoEncuesta> findByParticipanteIdParticipante(String idParticipante);
}