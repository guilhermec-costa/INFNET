import java.util.Scanner;
import java.util.Random;

public class Exercicios {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        exercicio1(scanner);
        exercicio2(scanner);
        exercicio3(scanner);
        exercicio4(scanner);
        exercicio5(scanner);
        exercicio6(scanner);
        exercicio7(scanner);
        exercicio8(scanner);
        exercicio9(scanner);
        exercicio10(scanner);
        exercicio11(scanner);
        exercicio12(scanner);
        scanner.close();
    }

    static void exercicio1(Scanner scanner) {
        System.out.print("Digite seu nome completo: ");
        String nome = scanner.nextLine();
        System.out.print("Digite sua idade: ");
        int idade = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Digite o nome da sua mãe: ");
        String mae = scanner.nextLine();
        System.out.print("Digite o nome do seu pai: ");
        String pai = scanner.nextLine();

        System.out.println("\n--- Informações Cadastradas ---");
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Mãe: " + mae);
        System.out.println("Pai: " + pai);

        if (nome.length() > mae.length() && nome.length() > pai.length()) {
            System.out.println("Seu nome é maior que o da sua mãe e do seu pai.");
        } else {
            System.out.println("Seu nome não é maior que o da sua mãe e do seu pai.");
        }
    }

    static void exercicio2(Scanner scanner) {
        double soma = 0;
        for (int i = 1; i <= 4; i++) {
            System.out.print("Digite a nota " + i + ": ");
            soma += scanner.nextDouble();
        }
        double media = soma / 4;
        System.out.println("Média: " + media);
        if (media >= 7) {
            System.out.println("Aprovado!");
        } else if (media >= 5) {
            System.out.println("Recuperação.");
        } else {
            System.out.println("Reprovado.");
        }
    }

    static void exercicio3(Scanner scanner) {
        System.out.print("Digite o valor em reais: ");
        double reais = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Digite a moeda de destino (dolar, euro, libra): ");
        String moeda = scanner.nextLine().toLowerCase();
        double convertido = 0;
        switch (moeda) {
            case "dolar": convertido = reais * 0.20; break;
            case "euro": convertido = reais * 0.18; break;
            case "libra": convertido = reais * 0.16; break;
            default: System.out.println("Moeda inválida."); break;
        }
        if (convertido > 0)
            System.out.printf("Valor convertido: %.2f %s\n", convertido, moeda);
    }

    static void exercicio4(Scanner scanner) {
        System.out.print("Digite o dia de nascimento: ");
        int dia = scanner.nextInt();
        System.out.print("Digite o mês de nascimento: ");
        int mes = scanner.nextInt();
        System.out.print("Digite o ano de nascimento: ");
        int ano = scanner.nextInt();

        int anoAtual = 2025, mesAtual = 5, diaAtual = 18;
        int idadeDias = (anoAtual - ano) * 365 + (mesAtual - mes) * 30 + (diaAtual - dia);

        int bissextos = 0;
        for (int i = ano; i <= anoAtual; i++) {
            if ((i % 4 == 0 && i % 100 != 0) || i % 400 == 0) {
                bissextos++;
            }
        }
        idadeDias += bissextos;
        System.out.println("Idade aproximada em dias: " + idadeDias);
    }

    static void exercicio5(Scanner scanner) {
        System.out.print("Digite o valor da compra: ");
        double valorCompra = scanner.nextDouble();
        double desconto = 0;
        if (valorCompra > 1000) desconto = 0.10;
        else if (valorCompra >= 500) desconto = 0.05;

        double valorFinal = valorCompra * (1 - desconto);
        System.out.printf("Valor original: R$ %.2f\n", valorCompra);
        System.out.printf("Desconto: %.0f%%\n", desconto * 100);
        System.out.printf("Valor com desconto: R$ %.2f\n", valorFinal);
    }

    static void exercicio6(Scanner scanner) {
        System.out.print("Digite um ano: ");
        int ano = scanner.nextInt();
        boolean bissexto = (ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0);
        System.out.println("O ano " + ano + (bissexto ? " é " : " não é ") + "bissexto.");
    }

    static void exercicio7(Scanner scanner) {
        System.out.print("Digite o salário bruto anual: ");
        double salario = scanner.nextDouble();
        double imposto = 0;
        if (salario <= 22847.76) imposto = 0;
        else if (salario <= 33919.80) imposto = (salario - 22847.76) * 0.075;
        else if (salario <= 45012.60) imposto = (salario - 33919.80) * 0.15 + 828.39;
        else imposto = (salario - 45012.60) * 0.225 + 2104.45;

        System.out.printf("Imposto a pagar: R$ %.2f\n", imposto);
        System.out.printf("Salário líquido: R$ %.2f\n", salario - imposto);
    }

    static void exercicio8(Scanner scanner) {
        System.out.print("Lado 1: ");
        int a = scanner.nextInt();
        System.out.print("Lado 2: ");
        int b = scanner.nextInt();
        System.out.print("Lado 3: ");
        int c = scanner.nextInt();
        if (a + b > c && a + c > b && b + c > a) {
            if (a == b && b == c) System.out.println("Equilátero");
            else if (a == b || a == c || b == c) System.out.println("Isósceles");
            else System.out.println("Escaleno");
        } else {
            System.out.println("As medidas não formam um triângulo.");
        }
    }

    static void exercicio9(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Cadastre uma senha: ");
        String senhaCerta = scanner.nextLine();
        String tentativa;
        do {
            System.out.print("Digite novamente a senha: ");
            tentativa = scanner.nextLine();
        } while (!tentativa.equals(senhaCerta));
        System.out.println("Senha correta!");
    }

    static void exercicio10(Scanner scanner) {
        Random random = new Random();
        int numeroSecreto = random.nextInt(100) + 1;
        int palpite;
        do {
            System.out.print("Adivinhe o número (1 a 100): ");
            palpite = scanner.nextInt();
            if (palpite < numeroSecreto) System.out.println("Muito baixo!");
            else if (palpite > numeroSecreto) System.out.println("Muito alto!");
        } while (palpite != numeroSecreto);
        System.out.println("Parabéns! Você acertou!");
    }

    static void exercicio11(Scanner scanner) {
        System.out.print("Valor inicial: ");
        int inicial = scanner.nextInt();
        System.out.print("Incremento: ");
        int incremento = scanner.nextInt();
        System.out.print("Sequência: ");
        for (int i = inicial; i <= 100; i += incremento) {
            System.out.print(i + (i + incremento <= 100 ? ", " : "\n"));
        }
    }

    static void exercicio12(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Digite uma frase: ");
        String frase = scanner.nextLine();
        String[] palavras = frase.trim().split("\\s+");
        System.out.println("Número de palavras: " + palavras.length);
    }
}
