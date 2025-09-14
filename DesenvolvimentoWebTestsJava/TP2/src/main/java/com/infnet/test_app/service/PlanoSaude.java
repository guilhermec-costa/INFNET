package com.infnet.test_app.service;

import com.infnet.test_app.models.Paciente;

public interface PlanoSaude {
    double getPercentualCobertura(Paciente paciente);
}