package com.softwareubb.proyecto_software.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pacientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El RUT es obligatorio")
    @Size(min = 8, max = 10, message = "El RUT debe tener un formato válido")
    @Column(unique = true)
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String apellido;
    
    @Lob
    @Column(name = "datos_formulario_json")
    private String datosFormularioJson;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }
    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDatosFormularioJson() {
        return datosFormularioJson;
    }
    public void setDatosFormularioJson(String datosFormularioJson) {
        this.datosFormularioJson = datosFormularioJson;
    }
}