package com.ubb.proyecto.repository;

import com.ubb.proyecto.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    // Método para la validación de unicidad (HU #11)
    Optional<Paciente> findByRut(String rut);
}