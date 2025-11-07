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

<<<<<<< HEAD
    @Column(nullable = false, unique = true) //HU 24: El cliente necesita que no se permita la creación de pacientes duplicados
=======
    @NotBlank(message = "El RUT es obligatorio")
    @Size(min = 8, max = 10, message = "El RUT debe tener un formato válido")
    @Column(unique = true)
>>>>>>> 31417c53bed333b51d2c19a10bc759766a432675
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String datosFormularioJson;
}