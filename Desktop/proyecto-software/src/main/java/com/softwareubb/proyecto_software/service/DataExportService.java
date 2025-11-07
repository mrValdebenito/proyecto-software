package com.softwareubb.proyecto_software.service;

import com.softwareubb.proyecto_software.model.Paciente;
import com.softwareubb.proyecto_software.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataExportService {

    private final PacienteRepository pacienteRepository;

    public DataExportService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public String generateCSVExport(boolean aplicarDicotomizacion) {
        List<Paciente> pacientes = pacienteRepository.findAll();
        StringBuilder csvContent = new StringBuilder();

        csvContent.append("ID,RUT,Nombre,Tiene_Cancer,Variable_Dicotomizada\n");

        for (Paciente p : pacientes) {
            csvContent.append(p.getId()).append(",");
            csvContent.append(escapeSpecialCharacters(p.getRut())).append(",");
            csvContent.append(escapeSpecialCharacters(p.getNombre())).append(",");
            csvContent.append(p.getTieneCancer() != null ? p.getTieneCancer() : "N/A").append(",");

            int edadSimulada = (int) (Math.random() * 80) + 20;

            if (aplicarDicotomizacion) {
                csvContent.append(dichotomizeValue(edadSimulada, 60));
            } else {
                csvContent.append(edadSimulada);
            }

            csvContent.append("\n");
        }

        return csvContent.toString();
    }

    public int dichotomizeValue(int valor, int umbral) {
        return valor > umbral ? 1 : 0;
    }

    private String escapeSpecialCharacters(String data) {
        if (data == null) {
            return "";
        }
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}