package com.proyecto.IngSoftware.repository;

import com.proyecto.IngSoftware.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Importar Optional si deseas usar findByRut o simplemente boolean

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, String> {
    
    // Agrega este método para verificar existencia por RUT de forma eficiente
    boolean existsByRut(String rut);

    // Opcional: si alguna vez necesitas obtener el participante completo por RUT
    Optional<Participante> findByRut(String rut);
}