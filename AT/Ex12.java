import java.util.Scanner;

public class Ex12 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String[] mensagens = new String[10];
        
        System.out.println("===== SISTEMA DE CHAT SIMPLES =====");
        System.out.println("Bem-vindos ao sistema de chat!");
        System.out.println();
        
        System.out.print("Digite o nome do primeiro usuário: ");
        String usuario1 = scanner.nextLine();
        
        System.out.print("Digite o nome do segundo usuário: ");
        String usuario2 = scanner.nextLine();
        
        System.out.println();
        System.out.println("Ótimo, " + usuario1 + " e " + usuario2 + "!");
        System.out.println("Cada um pode enviar até 5 mensagens. Vamos começar!");
        System.out.println();
        
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                System.out.print(usuario1 + ", digite sua mensagem: ");
                String mensagem = scanner.nextLine();
                mensagens[i] = usuario1 + ": " + mensagem;
            } else {
                System.out.print(usuario2 + ", digite sua mensagem: ");
                String mensagem = scanner.nextLine();
                mensagens[i] = usuario2 + ": " + mensagem;
            }
        }
        
        System.out.println();
        System.out.println("===== Histórico de Mensagens =====");
        
        for (int i = 0; i < mensagens.length; i++) {
            System.out.println(mensagens[i]);
        }
        
        System.out.println("==================================");
        System.out.println();
        
        System.out.println("Obrigado por utilizarem o sistema! Boa sorte para vocês! 🚀");
        
        scanner.close();
    }
}