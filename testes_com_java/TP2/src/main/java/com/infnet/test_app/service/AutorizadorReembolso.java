package com.infnet.test_app.service;

import com.infnet.test_app.models.Consulta;

public interface AutorizadorReembolso {
    boolean autorizar(Consulta consulta);
}