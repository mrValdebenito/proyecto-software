package com.softwareubb.proyecto_software.model;

import com.fasterxml.jackson.annotation.JsonIgnore; // <-- ¡Importar esto!
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "preguntas")
@Data
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPregunta;

    private String codigo;
    private boolean obligatoria;
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_formulario")
    @JsonIgnore // <-- ¡AÑADE ESTA LÍNEA!
    private Formulario formulario;
}