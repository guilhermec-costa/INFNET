public class Ex9 {

  static class ContaBancaria {
    private String titular;
    private double saldo;

    public ContaBancaria(String titular, double saldoInicial) {
      this.titular = titular;
      this.saldo = saldoInicial;
    }

    public void depositar(double valor) {
      if (valor > 0) {
        saldo += valor;
        System.out.printf("Depósito de R$ %.2f realizado com sucesso!%n", valor);
      } else {
        System.out.println("Erro: Valor de depósito deve ser positivo.");
      }
    }

    public void sacar(double valor) {
      if (valor > 0) {
        if (valor <= saldo) {
          saldo -= valor;
          System.out.printf("Saque de R$ %.2f realizado com sucesso!%n", valor);
        } else {
          System.out.println("Erro: Saldo insuficiente para realizar o saque.");
          System.out.printf("Saldo disponível: R$ %.2f%n", saldo);
        }
      } else {
        System.out.println("Erro: Valor de saque deve ser positivo.");
      }
    }

    public void exibirSaldo() {
      System.out.println("===== EXTRATO DA CONTA =====");
      System.out.println("Titular: " + titular);
      System.out.printf("Saldo atual: R$ %.2f%n", saldo);
      System.out.println("============================");
    }

  }

  public static void main(String[] args) {
    System.out.println("SISTEMA BANCÁRIO");
    System.out.println();

    ContaBancaria conta = new ContaBancaria("Ana Paula", 1000.00);

    conta.exibirSaldo();
    System.out.println();

    System.out.println("=== OPERAÇÕES BANCÁRIAS ===");

    conta.depositar(500.00);
    conta.exibirSaldo();
    System.out.println();

    conta.sacar(200.00);
    conta.exibirSaldo();
    System.out.println();

    conta.sacar(2000.00);
    conta.exibirSaldo();
    System.out.println();

    conta.depositar(-100.00);
    conta.exibirSaldo();
    System.out.println();

    conta.sacar(800.00);
    conta.exibirSaldo();
  }
}