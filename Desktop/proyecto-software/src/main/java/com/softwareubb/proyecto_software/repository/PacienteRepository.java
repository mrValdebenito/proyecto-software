package com.softwareubb.proyecto_software.repository;

import com.softwareubb.proyecto_software.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    // Método para la validación de unicidad (HU #11)
    Optional<Paciente> findByRut(String rut);
}