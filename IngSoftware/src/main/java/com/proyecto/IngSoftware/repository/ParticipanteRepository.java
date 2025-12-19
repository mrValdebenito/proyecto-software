package com.proyecto.IngSoftware.repository;

import com.proyecto.IngSoftware.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, String> {
    

    boolean existsByRut(String rut);

    // NUEVO MÉTODO DE BÚSQUEDA 
    List<Participante> findByNombreContainingIgnoreCase(String nombre);
}