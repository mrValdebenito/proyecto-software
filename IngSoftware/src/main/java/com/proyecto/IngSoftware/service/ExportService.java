package com.proyecto.IngSoftware.service;

import com.proyecto.IngSoftware.model.Participante;
import java.io.Writer;
import java.util.List;

public interface ExportService {
    void generarCsv(Writer writer, List<Participante> participantes);
}