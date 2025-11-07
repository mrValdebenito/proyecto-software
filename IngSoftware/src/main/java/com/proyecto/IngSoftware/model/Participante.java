package com.proyecto.IngSoftware.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Participante")
public class Participante {

    @Id
    // La anotación @GeneratedValue no es necesaria aquí ya que id_participante es asignado por ti (ej. P130, C003)
    @Column(name = "id_participante", length = 10)
    private String idParticipante;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "rut", unique = true, nullable = false, length = 12)
    private String rut;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "sexo", nullable = false, length = 5)
    private String sexo; // Mapeado como String, el ENUM de MySQL se maneja automáticamente

    @Column(name = "peso", precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(name = "email")
    private String email;

    @Column(name = "es_caso", nullable = false)
    private Boolean esCaso;

    // --- Constructor sin argumentos (necesario para JPA) ---
    public Participante() {}

    public String getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(String idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEsCaso() {
        return esCaso;
    }

    public void setEsCaso(Boolean esCaso) {
        this.esCaso = esCaso;
    }
}