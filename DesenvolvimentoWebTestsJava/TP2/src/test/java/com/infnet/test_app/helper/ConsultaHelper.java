package com.infnet.test_app.helper;

import com.infnet.test_app.models.Consulta;
import com.infnet.test_app.models.Paciente;

public class ConsultaHelper {

    public static Consulta criarConsulta(String nomePaciente, double valor) {
        return new Consulta(new Paciente(nomePaciente), valor);
    }

    public static Consulta consultaPadrao() {
        return criarConsulta("Paciente Padrão", 100.0);
    }
}