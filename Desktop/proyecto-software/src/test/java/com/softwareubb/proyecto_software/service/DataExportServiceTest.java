package com.softwareubb.proyecto_software.service;

import com.softwareubb.proyecto_software.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataExportServiceTest {

    // Declaramos el servicio que queremos probar
    private DataExportService dataExportService;

    // "Mockeamos" (simulamos) las dependencias que no queremos probar
    @Mock
    private PacienteRepository pacienteRepository;

    @BeforeEach
    void setUp() {
        // Inicializa los Mocks
        MockitoAnnotations.openMocks(this);
        // Creamos una instancia real del servicio, pasándole el repositorio simulado
        dataExportService = new DataExportService(pacienteRepository);
    }

    @Test
    void testDichotomizeValue() {
        int umbral = 60;
        
        
        int resultadoSuperior = dataExportService.dichotomizeValue(65, umbral);
        assertEquals(1, resultadoSuperior, "Un valor (65) mayor al umbral (60) debería ser 1");

        int resultadoInferior = dataExportService.dichotomizeValue(50, umbral);
        assertEquals(0, resultadoInferior, "Un valor (50) menor al umbral (60) debería ser 0");

        int resultadoIgual = dataExportService.dichotomizeValue(60, umbral);
        assertEquals(0, resultadoIgual, "Un valor (60) igual al umbral (60) debería ser 0");
    }
}