package com.softwareubb.proyecto_software.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "formularios")
@Data
public class Formulario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFormulario;

    private String version;
    private String vigencia;

    @OneToMany(mappedBy = "formulario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pregunta> preguntas;
}