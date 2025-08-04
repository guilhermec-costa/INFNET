package ex8;

public class Ex8 {
    public static void main(String[] args) {
        System.out.println("===== SISTEMA DE FUNCIONÁRIOS =====");
        System.out.println();
        
        Gerente gerente = new Gerente("Maria Silva", 5000.00);
        Estagiario estagiario = new Estagiario("João Santos", 1200.00);
        
        gerente.exibirInfo();
        estagiario.exibirInfo();
        
        System.out.println();
        System.out.println("===== RESUMO FINANCEIRO =====");
        System.out.printf("Total de salário do gerente: R$ %.2f%n", gerente.calcularSalario());
        System.out.printf("Total de salário do estagiário: R$ %.2f%n", estagiario.calcularSalario());
        System.out.printf("Total geral de salários: R$ %.2f%n", 
                         gerente.calcularSalario() + estagiario.calcularSalario());
    }
}