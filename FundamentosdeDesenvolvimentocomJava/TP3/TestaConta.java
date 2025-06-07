package TP3;

// EXERCÍCIO 9 - Classe de teste para Conta
class TestaConta {
    public static void main(String[] args) {
        System.out.println("=== TESTE DO SISTEMA BANCÁRIO ===\n");
        
        // Instanciando um objeto da classe Conta
        Conta minhaConta = new Conta();
        
        // Atribuindo valores aos atributos
        minhaConta.titular = "João Silva";
        minhaConta.numero = 12345;
        minhaConta.agencia = "0001";
        minhaConta.saldo = 1000.00;
        minhaConta.dataAbertura = "15/03/2024";
        
        // Exibindo dados iniciais
        minhaConta.exibirDadosConta();
        
        // Testando depósito
        System.out.println("\n--- REALIZANDO DEPÓSITO ---");
        minhaConta.deposita(500.00);
        
        // Testando saque
        System.out.println("\n--- REALIZANDO SAQUE ---");
        minhaConta.saca(200.00);
        
        // Calculando rendimento
        System.out.println("\n--- CALCULANDO RENDIMENTO ---");
        double rendimento = minhaConta.calculaRendimento();
        System.out.println("Rendimento mensal (10%): R$ " + rendimento);
        
        // Exibindo dados finais
        System.out.println("\n--- DADOS FINAIS DA CONTA ---");
        minhaConta.exibirDadosConta();
    }
}