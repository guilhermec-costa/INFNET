package com.infnet.test_app;

public class CalculadoraReembolso {

    public double calcularReembolso(double valorConsulta, double percentualCobertura) {
        return valorConsulta * (percentualCobertura / 100.0);
    }
}
