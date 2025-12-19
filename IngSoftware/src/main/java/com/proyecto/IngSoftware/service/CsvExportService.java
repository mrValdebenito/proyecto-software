package com.proyecto.IngSoftware.service;

import com.proyecto.IngSoftware.model.Participante;
import org.springframework.stereotype.Service;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

/**
 * Implementación del servicio de exportación para formato CSV.
 * Genera un archivo de texto separado por comas compatible con Excel.
 */
@Service
public class CsvExportService implements ExportService {

    /**
     * Escribe la lista de participantes en el Writer proporcionado en formato CSV.
     * Incluye una cabecera con los nombres de las columnas.
     *
     * @param writer Flujo de escritura donde se volcarán los datos.
     * @param participantes Lista de objetos a exportar.
     */
    @Override
    public void generarCsv(Writer writer, List<Participante> participantes) {
        PrintWriter printWriter = new PrintWriter(writer);
        // Cabecera
        printWriter.println("ID,Nombre,RUT,Email,Sexo,Peso,Tipo");

        // Iteración de datos
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