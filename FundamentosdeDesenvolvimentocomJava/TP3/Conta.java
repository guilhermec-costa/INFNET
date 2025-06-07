package TP3;
// ========== EXERCÍCIOS 7, 8 e 9 - SISTEMA BANCÁRIO ==========

class Conta {
    // EXERCÍCIO 7 - Atributos da conta bancária
    String titular;
    int numero;
    String agencia;
    double saldo;
    String dataAbertura;
    
    // EXERCÍCIO 8 - Métodos da conta
    void saca(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque de R$ " + valor + " realizado com sucesso.");
            System.out.println("Saldo atual: R$ " + saldo);
        } else {
            System.out.println("Saque não realizado. Valor inválido ou saldo insuficiente.");
        }
    }
    
    void deposita(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("Depósito de R$ " + valor + " realizado com sucesso.");
            System.out.println("Saldo atual: R$ " + saldo);
        } else {
            System.out.println("Valor de depósito deve ser positivo.");
        }
    }
    
    double calculaRendimento() {
        double rendimento = saldo * 0.1;
        return rendimento;
    }
    
    void exibirDadosConta() {
        System.out.println("=== DADOS DA CONTA ===");
        System.out.println("Titular: " + titular);
        System.out.println("Número: " + numero);
        System.out.println("Agência: " + agencia);
        System.out.println("Saldo: R$ " + saldo);
        System.out.println("Data de Abertura: " + dataAbertura);
        System.out.println("======================");
    }
}
