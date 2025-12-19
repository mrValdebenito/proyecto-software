package com.proyecto.IngSoftware.service;

import com.proyecto.IngSoftware.model.Participante;
import org.springframework.stereotype.Service;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

@Service
public class CsvExportService implements ExportService {

    @Override
    public void generarCsv(Writer writer, List<Participante> participantes) {
        PrintWriter printWriter = new PrintWriter(writer);
        // Cabecera del archivo
        printWriter.println("ID,Nombre,RUT,Email,Sexo,Peso,Tipo");

        // Datos de cada participante
        for (Participante p : participantes) {
            printWriter.printf("%s,%s,%s,%s,%s,%s,%s%n",
                    p.getIdParticipante(),
                    p.getNombre(),
                    p.getRut(),
                    p.getEmail() != null ? p.getEmail() : "Sin email",
                    p.getSexo(),
                    p.getPeso() != null ? p.getPeso() : 0,
                    p.getEsCaso() ? "Caso" : "Control");
        }
    }
}