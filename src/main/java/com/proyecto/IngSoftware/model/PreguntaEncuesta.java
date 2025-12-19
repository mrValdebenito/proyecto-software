// Archivo: src/main/java/com/proyecto/IngSoftware/model/PreguntaEncuesta.java

package com.proyecto.IngSoftware.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Pregunta_Encuesta")
public class PreguntaEncuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pregunta")
    private Integer idPregunta;

    @Column(name = "enunciado", nullable = false)
    private String enunciado;

    @Column(name = "categoria")
    private String categoria;

    // Constructores, Getters y Setters...
    public PreguntaEncuesta() {}

    public Integer getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}