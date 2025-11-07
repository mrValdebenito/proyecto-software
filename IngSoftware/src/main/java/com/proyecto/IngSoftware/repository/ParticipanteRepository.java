package com.proyecto.IngSoftware.repository;

import com.proyecto.IngSoftware.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, String> {
    // Hereda métodos: findAll(), findById(id), save(participante), deleteById(id), etc.
    // También puedes agregar métodos personalizados aquí:
    // List<Participante> findByEsCaso(Boolean esCaso); 
}