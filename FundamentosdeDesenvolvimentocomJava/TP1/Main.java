import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

class Main {

  public static void println(String text) {
    System.out.println(text);
  }

  public static void printInline(String text) {
    System.out.print(text);
  }

  public static void exerc4() {
    println(Utils.exerciseHeader(4));
    println("Hello world");
  }

  public static void exerc7() {
    println(Utils.exerciseHeader(7));
    String name = "Guilherme China";
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
    Date now = new Date();
    println("My name: " + name);
    println("Current date: " + dateFormatter.format(now));
  }

  public static void exerc8() {
    println(Utils.exerciseHeader(8));
    Double salary = 4500.50;
    String dogName = "Churros";
    int age = 20;

    println("Dog name: " + dogName);
    println("Salary: " + salary.toString());
    println("Age: " + age);
  }

  public static void exerc9() {
    println(Utils.exerciseHeader(9));
    try(Scanner inputScanner = new Scanner(System.in)) {
      printInline("Type your name: ");
      final String name = inputScanner.nextLine();
      printInline("Type your age: ");
      final int age = inputScanner.nextInt();
      println(String.format("Name: %s, Age: %d", name, age));
    }
  }

  public static void main(String[] args) {
    IAnimal churros = new Dog();
    IAnimal gato = new Cat();

    churros.eat("Ração");
    gato.eat("Bosta");
  }
}