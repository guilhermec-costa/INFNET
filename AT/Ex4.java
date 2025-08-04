import java.util.Scanner;

public class Ex4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Digite o nome do cliente: ");
        String nomeCliente = scanner.nextLine();
        
        System.out.print("Digite o valor do empréstimo: R$ ");
        double valorEmprestimo = scanner.nextDouble();
        
        int numeroParcelas;
        do {
            System.out.print("Digite o número de parcelas (mínimo 6, máximo 48): ");
            numeroParcelas = scanner.nextInt();
            
            if (numeroParcelas < 6 || numeroParcelas > 48) {
                System.out.println("Erro: O número de parcelas deve estar entre 6 e 48.");
            }
        } while (numeroParcelas < 6 || numeroParcelas > 48);
        
        double taxaJuros = 0.03;
        double valorTotalPago = valorEmprestimo * Math.pow(1 + taxaJuros, numeroParcelas);
        double valorParcela = valorTotalPago / numeroParcelas;
        
        System.out.println("\n===== SIMULAÇÃO DE EMPRÉSTIMO =====");
        System.out.println("Cliente: " + nomeCliente);
        System.out.printf("Valor do empréstimo: R$ %.2f%n", valorEmprestimo);
        System.out.println("Número de parcelas: " + numeroParcelas);
        System.out.printf("Taxa de juros mensal: %.1f%%%n", taxaJuros * 100);
        System.out.printf("Valor total a ser pago: R$ %.2f%n", valorTotalPago);
        System.out.printf("Valor da parcela mensal: R$ %.2f%n", valorParcela);
        System.out.printf("Total de juros: R$ %.2f%n", valorTotalPago - valorEmprestimo);
        
        scanner.close();
    }
}