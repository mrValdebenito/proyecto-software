// Archivo: src/main/java/com/proyecto/IngSoftware/model/DatoEncuesta.java

package com.proyecto.IngSoftware.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Dato_Encuesta")
public class DatoEncuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dato")
    private Long idDato;

    // Relación N:1 con Participante
    @ManyToOne
    @JoinColumn(name = "id_participante", nullable = false)
    private Participante participante;

    // Relación N:1 con PreguntaEncuesta
    @ManyToOne
    @JoinColumn(name = "id_pregunta", nullable = false)
    private PreguntaEncuesta pregunta;

    @Column(name = "valor_dicotomizado", nullable = false, length = 50)
    private String valorDicotomizado;

    // Relación N:1 con Usuario (Encuestador)
    @ManyToOne
    @JoinColumn(name = "id_encuestador", nullable = false)
    private Usuario encuestador;

    @Column(name = "fecha_ingreso")
    private LocalDateTime fechaIngreso;

    // Constructores, Getters y Setters...
    public DatoEncuesta() {
        this.fechaIngreso = LocalDateTime.now();
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Usuario getEncuestador() {
        return encuestador;
    }

    public void setEncuestador(Usuario encuestador) {
        this.encuestador = encuestador;
    }

    public String getValorDicotomizado() {
        return valorDicotomizado;
    }

    public void setValorDicotomizado(String valorDicotomizado) {
        this.valorDicotomizado = valorDicotomizado;
    }

    public PreguntaEncuesta getPregunta() {
        return pregunta;
    }

    public void setPregunta(PreguntaEncuesta pregunta) {
        this.pregunta = pregunta;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public Long getIdDato() {
        return idDato;
    }

    public void setIdDato(Long idDato) {
        this.idDato = idDato;
    }
}