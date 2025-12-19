package com.proyecto.IngSoftware.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*; // <--- IMPORTANTE: Agregar este import

@Entity
@Table(name = "Participante")
public class Participante {

    @Id
    @Column(name = "id_participante", length = 10)
    private String idParticipante;

    @NotBlank(message = "El nombre es obligatorio") // <--- Nueva validación
    @Size(min = 2, message = "El nombre debe tener al menos 2 letras") // <--- Nueva validación
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "El RUT es obligatorio") // <--- Nueva validación
    @Column(name = "rut", unique = true, nullable = false, length = 12)
    private String rut;

    @NotNull(message = "La fecha de nacimiento es obligatoria") // <--- Nueva validación
    @Past(message = "La fecha debe ser en el pasado") // <--- Nueva validación
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @NotNull(message = "El sexo es obligatorio") // <--- Nueva validación
    @Column(name = "sexo", nullable = false, length = 5)
    private String sexo;

    @Min(value = 1, message = "El peso debe ser mayor a 0") // <--- Nueva validación
    @Column(name = "peso", precision = 5, scale = 2)
    private BigDecimal peso;

    @Email(message = "Formato de correo inválido") // <--- Nueva validación
    @Column(name = "email")
    private String email;

    @NotNull(message = "Debe indicar si es Caso o Control") // <--- Nueva validación
    @Column(name = "es_caso", nullable = false)
    private Boolean esCaso;

    
    public Participante() {}

    // --- Getters y Setters -
    public String getIdParticipante() { return idParticipante; }
    public void setIdParticipante(String idParticipante) { this.idParticipante = idParticipante; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public BigDecimal getPeso() { return peso; }
    public void setPeso(BigDecimal peso) { this.peso = peso; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Boolean getEsCaso() { return esCaso; }
    public void setEsCaso(Boolean esCaso) { this.esCaso = esCaso; }
}