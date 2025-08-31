package com.infnet.test_app.service;

import com.infnet.test_app.models.Consulta;

public interface Auditoria {
    void registrarCalculo(Consulta consulta, double valorReembolsado);
}