class Program {

  static void Print(string msg) { Console.WriteLine(msg); }
  static void Exerc5() { Print("Hello world"); }

  static void Exerc8() {
    Print("Type your name: ");
    string name = Console.ReadLine();
    Print($"Hello, {name}!");
  }

  static void Exerc9() {
    int age = 25;
    string name = "Ana";
    double height = 1.68;

    Print("Name: " + name);
    Print("Age: " + age);
    Print("Height: " + height + "m");
  }
  static void Exerc10() {
    string name = "Lucas";
    int age = 22;

    Print($"My name is {name} and I'm {age} years old.");
  }

  static void Separator() {
    Print("-=-=-=-=-=-=-=-=-=-");
  }
  static void Main() {
    Separator();
    Exerc5();
    Separator();
    Exerc8();
    Separator();
    Exerc9();
    Separator();
    Exerc10();
    Separator();
  }
}
