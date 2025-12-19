package com.proyecto.IngSoftware.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

/**
 * Entidad que representa a un participante dentro del sistema de encuestas.
 * Contiene la información personal, demográfica y clínica básica.
 * * Se utiliza validación (Bean Validation) para asegurar la integridad de los datos
 * antes de ser persistidos en la base de datos.
 */
@Entity
@Table(name = "Participante")
public class Participante {

    /** Identificador único del participante (Rut o código interno). */
    @Id
    @Column(name = "id_participante", length = 10)
    private String idParticipante;

    /** Nombre completo del participante. No puede estar vacío. */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, message = "El nombre debe tener al menos 2 letras")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /** RUT o DNI del participante. Debe ser único en el sistema. */
    @NotBlank(message = "El RUT es obligatorio")
    @Column(name = "rut", unique = true, nullable = false, length = 12)
    private String rut;

    /** Fecha de nacimiento. Debe ser una fecha en el pasado. */
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha debe ser en el pasado")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    /** Sexo biológico o género del participante. */
    @NotNull(message = "El sexo es obligatorio")
    @Column(name = "sexo", nullable = false, length = 5)
    private String sexo;

    /** Peso corporal en Kilogramos. */
    @Min(value = 1, message = "El peso debe ser mayor a 0")
    @Column(name = "peso", precision = 5, scale = 2)
    private BigDecimal peso;

    /** Correo electrónico de contacto (opcional). */
    @Email(message = "Formato de correo inválido")
    @Column(name = "email")
    private String email;

    /** Indica si el participante pertenece al grupo CASO (true) o CONTROL (false). */
    @NotNull(message = "Debe indicar si es Caso o Control")
    @Column(name = "es_caso", nullable = false)
    private Boolean esCaso;

    // --- Constructor por defecto ---
    public Participante() {}

    // --- Getters y Setters ---
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