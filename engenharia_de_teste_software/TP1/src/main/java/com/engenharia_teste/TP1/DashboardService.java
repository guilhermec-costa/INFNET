package com.engenharia_teste.TP1;

public class DashboardService {
    
    private final CalculadoraIMCService calculadora;

    public DashboardService(CalculadoraIMCService calculadora) {
        this.calculadora = calculadora;
    }

    public String getMensagemSaude(double peso, double altura) {
        double imc = calculadora.calcularPeso(peso, altura);
        String classificacao = calculadora.classificarIMC(imc);
        
        if (classificacao.equals("Saudável")) {
            return "Parabéns! Você está saudável.";
        } else if (classificacao.contains("Magreza")) {
            return "Atenção! Risco de desnutrição.";
        } else {
            return "Atenção! Risco de sobrepeso.";
        }
    }
}