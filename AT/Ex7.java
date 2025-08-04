import java.util.Scanner;

public class Ex7 {

  static class Aluno {
    private String nome;
    private String matricula;
    private double nota1;
    private double nota2;
    private double nota3;

    Aluno(String nome, String matricula, double nota1, double nota2, double nota3) {
      this.nome = nome;
      this.matricula = matricula;
      this.nota1 = nota1;
      this.nota2 = nota2;
      this.nota3 = nota3;
    }

    public double calcularMedia() {
      return (nota1 + nota2 + nota3) / 3.0;
    }

    public void verificarAprovacao() {
      double media = calcularMedia();
      System.out.println("\n===== SITUAÇÃO ACADÊMICA =====");
      System.out.println("Nome: " + nome);
      System.out.println("Matrícula: " + matricula);
      System.out.printf("Nota 1: %.2f%n", nota1);
      System.out.printf("Nota 2: %.2f%n", nota2);
      System.out.printf("Nota 3: %.2f%n", nota3);
      System.out.printf("Média: %.2f%n", media);

      if (media >= 7.0) {
        System.out.println("Situação: APROVADO ✓");
      } else {
        System.out.println("Situação: REPROVADO ✗");
      }
      System.out.println("==============================");
    }

  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("SISTEMA ACADÊMICO - CADASTRO DE ALUNO");
    System.out.println();

    System.out.print("Digite o nome do aluno: ");
    String nome = scanner.nextLine();

    System.out.print("Digite a matrícula: ");
    String matricula = scanner.nextLine();

    System.out.print("Digite a primeira nota: ");
    double nota1 = scanner.nextDouble();

    System.out.print("Digite a segunda nota: ");
    double nota2 = scanner.nextDouble();

    System.out.print("Digite a terceira nota: ");
    double nota3 = scanner.nextDouble();

    Aluno aluno = new Aluno(nome, matricula, nota1, nota2, nota3);
    aluno.verificarAprovacao();

    scanner.close();
  }
}