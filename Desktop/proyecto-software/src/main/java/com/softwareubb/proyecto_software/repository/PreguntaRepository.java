package com.softwareubb.proyecto_software.repository;

import com.softwareubb.proyecto_software.model.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
}