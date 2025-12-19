package com.proyecto.IngSoftware.repository;

import com.proyecto.IngSoftware.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio JPA para la entidad Participante.
 * Proporciona operaciones CRUD estándar y consultas personalizadas.
 */
@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, String> {

    /**
     * Verifica si existe un participante con el RUT dado.
     * Útil para validaciones antes de crear nuevos registros.
     *
     * @param rut El RUT a verificar.
     * @return true si existe, false si no.
     */
    boolean existsByRut(String rut);

    /**
     * Busca participantes cuyo nombre contenga la cadena proporcionada,
     * ignorando mayúsculas y minúsculas.
     *
     * @param nombre Parte del nombre a buscar.
     * @return Lista de participantes que coinciden.
     */
    List<Participante> findByNombreContainingIgnoreCase(String nombre);
}