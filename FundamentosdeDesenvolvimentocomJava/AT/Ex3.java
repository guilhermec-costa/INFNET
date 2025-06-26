import java.util.Scanner;

public class Ex3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("Digite seu salário mensal: R$ ");
        double salarioMensal = scanner.nextDouble();
        
        double salarioAnual = salarioMensal * 12;
        double imposto = calcularImposto(salarioAnual);
        double salarioLiquido = salarioAnual - imposto;
        
        System.out.println("\n===== CÁLCULO DE IMPOSTO DE RENDA =====");
        System.out.println("Nome: " + nome);
        System.out.printf("Salário anual: R$ %.2f%n", salarioAnual);
        System.out.printf("Imposto devido: R$ %.2f%n", imposto);
        System.out.printf("Salário líquido anual: R$ %.2f%n", salarioLiquido);
        
        scanner.close();
    }
    
    public static double calcularImposto(double salarioAnual) {
        if (salarioAnual <= 22847.76) {
            return 0.0;
        } else if (salarioAnual <= 33919.80) {
            return (salarioAnual - 22847.76) * 0.075; // 7.5%
        } else if (salarioAnual <= 45012.60) {
            double imposto1 = (33919.80 - 22847.76) * 0.075;
            double imposto2 = (salarioAnual - 33919.80) * 0.15;
            return imposto1 + imposto2;
        } else {
            double imposto1 = (33919.80 - 22847.76) * 0.075;
            double imposto2 = (45012.60 - 33919.80) * 0.15;
            double imposto3 = (salarioAnual - 45012.60) * 0.275;
            return imposto1 + imposto2 + imposto3;
        }
    }
}