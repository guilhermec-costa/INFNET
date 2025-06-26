import java.io.*;
import java.util.Scanner;

public class Ex10 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nomeArquivo = "compras.txt";
        
        System.out.println("===== SISTEMA DE REGISTRO DE COMPRAS =====");
        System.out.println();
        
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write("===== REGISTRO DE COMPRAS =====\n");
            writer.write("Data: " + java.time.LocalDate.now() + "\n\n");
            
            for (int i = 1; i <= 3; i++) {
                System.out.println("=== COMPRA " + i + " ===");
                
                System.out.print("Digite o nome do produto: ");
                String produto = scanner.nextLine();
                
                System.out.print("Digite a quantidade: ");
                int quantidade = scanner.nextInt();
                
                System.out.print("Digite o preço unitário: R$ ");
                double precoUnitario = scanner.nextDouble();
                scanner.nextLine();
                
                double total = quantidade * precoUnitario;
                
                writer.write("Compra " + i + ":\n");
                writer.write("Produto: " + produto + "\n");
                writer.write("Quantidade: " + quantidade + "\n");
                writer.write(String.format("Preço Unitário: R$ %.2f%n", precoUnitario));
                writer.write(String.format("Total: R$ %.2f%n", total));
                writer.write("------------------------\n");
                
                System.out.printf("Compra registrada! Total: R$ %.2f%n", total);
                System.out.println();
            }
            
            System.out.println("Todas as compras foram salvas no arquivo " + nomeArquivo);
            
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
        
        System.out.println();
        System.out.println("===== LENDO COMPRAS DO ARQUIVO =====");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        
        scanner.close();
    }
}