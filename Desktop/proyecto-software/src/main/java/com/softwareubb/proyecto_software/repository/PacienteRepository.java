package com.softwareubb.proyecto_software.repository;

import com.softwareubb.proyecto_software.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByRut(String rut);

    List<Paciente> findByRutContainingOrNombreContaining(String rut, String nombre);

    List<Paciente> findByEdad(Integer edad);
}