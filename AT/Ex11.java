import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Ex11 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        System.out.println("===== SIMULAÇÃO DE LOTERIA =====");
        System.out.println("Bem-vindo ao jogo de loteria!");
        System.out.println("Você deve escolher 6 números entre 1 e 60.");
        System.out.println();
        
        int[] numerosSorteados = new int[6];
        for (int i = 0; i < 6; i++) {
            int numero;
            boolean repetido;
            do {
                numero = random.nextInt(60) + 1;
                repetido = false;
                for (int j = 0; j < i; j++) {
                    if (numerosSorteados[j] == numero) {
                        repetido = true;
                        break;
                    }
                }
            } while (repetido);
            numerosSorteados[i] = numero;
        }
        
        Arrays.sort(numerosSorteados);
        
        int[] numerosUsuario = new int[6];
        System.out.println("Digite seus 6 números:");
        
        for (int i = 0; i < 6; i++) {
            boolean numeroValido = false;
            while (!numeroValido) {
                System.out.print("Número " + (i + 1) + " (1-60): ");
                int numero = scanner.nextInt();
                
                if (numero < 1 || numero > 60) {
                    System.out.println("Erro: O número deve estar entre 1 e 60.");
                    continue;
                }
                
                boolean repetido = false;
                for (int j = 0; j < i; j++) {
                    if (numerosUsuario[j] == numero) {
                        repetido = true;
                        break;
                    }
                }
                
                if (repetido) {
                    System.out.println("Erro: Você já escolheu esse número.");
                } else {
                    numerosUsuario[i] = numero;
                    numeroValido = true;
                }
            }
        }
        
        Arrays.sort(numerosUsuario);
        
        int acertos = 0;
        int[] numerosAcertados = new int[6];
        int indexAcertos = 0;
        
        for (int numeroUsuario : numerosUsuario) {
            for (int numeroSorteado : numerosSorteados) {
                if (numeroUsuario == numeroSorteado) {
                    acertos++;
                    numerosAcertados[indexAcertos++] = numeroUsuario;
                    break;
                }
            }
        }
        
        System.out.println("\n===== RESULTADO DA LOTERIA =====");
        System.out.print("Números sorteados: ");
        for (int i = 0; i < numerosSorteados.length; i++) {
            System.out.print(numerosSorteados[i]);
            if (i < numerosSorteados.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        
        System.out.print("Seus números: ");
        for (int i = 0; i < numerosUsuario.length; i++) {
            System.out.print(numerosUsuario[i]);
            if (i < numerosUsuario.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        
        System.out.println("Você acertou: " + acertos + " número" + (acertos != 1 ? "s" : ""));
        
        if (acertos > 0) {
            System.out.print("Números acertados: ");
            for (int i = 0; i < acertos; i++) {
                System.out.print(numerosAcertados[i]);
                if (i < acertos - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
        
        System.out.println();
        switch (acertos) {
            case 0:
                System.out.println("Que pena! Você não acertou nenhum número. Tente novamente!");
                break;
            case 1:
            case 2:
                System.out.println("Você acertou alguns números, mas não ganhou prêmio. Continue tentando!");
                break;
            case 3:
                System.out.println("Parabéns! Você acertou 3 números e ganhou um prêmio pequeno!");
                break;
            case 4:
                System.out.println("Muito bem! Você acertou 4 números e ganhou um prêmio médio!");
                break;
            case 5:
                System.out.println("Excelente! Você acertou 5 números e ganhou um grande prêmio!");
                break;
            case 6:
                System.out.println("🎉 JACKPOT! Você acertou todos os 6 números! Você é o grande vencedor! 🎉");
                break;
        }
        
        System.out.println("\nObrigado por jogar!");
        scanner.close();
    }
}