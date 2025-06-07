using System;

class Program
{
    static void Main()
    {
        ExerciciosParte1();
        ExerciciosParte2();
        ExerciciosParte3();
        ExerciciosParte4();
    }

    static void ExerciciosParte1()
    {
        Exercicio1();
        Exercicio2();
        Exercicio3();
    }

    static void ExerciciosParte2()
    {
        Exercicio4();
        Exercicio5();
        Exercicio6();
    }

    static void ExerciciosParte3()
    {
        Exercicio7();
        Exercicio8();
        Exercicio9();
    }

    static void ExerciciosParte4()
    {
        Exercicio10();
        Exercicio11();
        Exercicio12();
    }

    static void Exercicio1()
    {
        Console.Write("Digite sua data de nascimento (dd/mm/aaaa): ");
        DateTime nascimento = DateTime.Parse(Console.ReadLine());
        DateTime hoje = DateTime.Today;
        int anos = hoje.Year - nascimento.Year;
        int meses = hoje.Month - nascimento.Month;
        int dias = hoje.Day - nascimento.Day;
        if (dias < 0)
        {
            meses--;
            dias += DateTime.DaysInMonth(hoje.Year, (hoje.Month == 1 ? 12 : hoje.Month - 1));
        }
        if (meses < 0)
        {
            anos--;
            meses += 12;
        }
        Console.WriteLine($"Idade: {anos} anos, {meses} meses e {dias} dias");
    }

    static void Exercicio2()
    {
        Console.Write("Digite sua data de nascimento (dd/mm/aaaa): ");
        DateTime nascimento = DateTime.Parse(Console.ReadLine());
        DateTime hoje = DateTime.Today;
        DateTime proximoAniversario = new DateTime(hoje.Year, nascimento.Month, nascimento.Day);
        if (proximoAniversario < hoje)
        {
            proximoAniversario = proximoAniversario.AddYears(1);
        }
        int diasFaltando = (proximoAniversario - hoje).Days;
        Console.WriteLine($"Faltam {diasFaltando} dias para o próximo aniversário");
    }

    static void Exercicio3()
    {
        Console.Write("Digite a primeira data (dd/mm/aaaa): ");
        DateTime data1 = DateTime.Parse(Console.ReadLine());
        Console.Write("Digite a segunda data (dd/mm/aaaa): ");
        DateTime data2 = DateTime.Parse(Console.ReadLine());
        if (data2 < data1)
        {
            var temp = data1;
            data1 = data2;
            data2 = temp;
        }
        int anos = data2.Year - data1.Year;
        int meses = data2.Month - data1.Month;
        int dias = data2.Day - data1.Day;
        if (dias < 0)
        {
            meses--;
            dias += DateTime.DaysInMonth(data2.Year, (data2.Month == 1 ? 12 : data2.Month - 1));
        }
        if (meses < 0)
        {
            anos--;
            meses += 12;
        }
        Console.WriteLine($"Diferença: {anos} anos, {meses} meses e {dias} dias");
    }

    static void Exercicio4()
    {
        Console.Write("Nome: ");
        string nome = Console.ReadLine();
        Console.Write("Idade: ");
        string idade = Console.ReadLine();
        Console.Write("Telefone: ");
        string telefone = Console.ReadLine();
        Console.Write("E-mail: ");
        string email = Console.ReadLine();
        Console.WriteLine($"Nome: {nome}\nIdade: {idade}\nTelefone: {telefone}\nE-mail: {email}");
    }

    static void Exercicio5()
    {
        Console.Write("Digite a temperatura em Celsius: ");
        double celsius = double.Parse(Console.ReadLine());
        double fahrenheit = celsius * 9 / 5 + 32;
        double kelvin = celsius + 273.15;
        Console.WriteLine($"Fahrenheit: {fahrenheit:F2}\nKelvin: {kelvin:F2}");
    }

    static void Exercicio6()
    {
        Console.Write("Digite o peso (kg): ");
        double peso = double.Parse(Console.ReadLine());
        Console.Write("Digite a altura (m): ");
        double altura = double.Parse(Console.ReadLine());
        double imc = peso / (altura * altura);
        string classificacao = "";
        if (imc < 18.5) classificacao = "Abaixo do peso";
        else if (imc < 25) classificacao = "Peso normal";
        else if (imc < 30) classificacao = "Sobrepeso";
        else if (imc < 35) classificacao = "Obesidade grau I";
        else if (imc < 40) classificacao = "Obesidade grau II";
        else classificacao = "Obesidade grau III";
        Console.WriteLine($"IMC: {imc:F2} - {classificacao}");
    }

    static void Exercicio7()
    {
        Console.Write("Digite um número inteiro: ");
        int numero = int.Parse(Console.ReadLine());
        Console.WriteLine(numero % 2 == 0 ? "Par" : "Ímpar");
    }

    static void Exercicio8()
    {
        Console.Write("Digite uma nota (0 a 10): ");
        double nota = double.Parse(Console.ReadLine());
        string classificacao = "";
        if (nota < 5) classificacao = "Insuficiente";
        else if (nota < 7) classificacao = "Regular";
        else if (nota < 9) classificacao = "Bom";
        else classificacao = "Excelente";
        Console.WriteLine($"Classificação: {classificacao}");
    }

    static void Exercicio9()
    {
        Console.Write("Digite o salário bruto: ");
        double salario = double.Parse(Console.ReadLine());
        double desconto = 0;
        if (salario <= 2000) desconto = 0;
        else if (salario <= 3000) desconto = (salario - 2000) * 0.08;
        else if (salario <= 4500) desconto = (1000 * 0.08) + ((salario - 3000) * 0.18);
        else desconto = (1000 * 0.08) + (1500 * 0.18) + ((salario - 4500) * 0.28);
        double liquido = salario - desconto;
        Console.WriteLine($"Salário Bruto: {salario:F2}\nDesconto: {desconto:F2}\nSalário Líquido: {liquido:F2}");
    }

    static void Exercicio10()
    {
        Console.Write("Digite um número para a contagem regressiva: ");
        int numero = int.Parse(Console.ReadLine());
        for (int i = numero; i >= 0; i--)
        {
            if (i == 0) Console.Write(i);
            else Console.Write(i + ", ");
        }
        Console.WriteLine();
    }

    static void Exercicio11()
    {
        Console.Write("Digite um número para ver a tabuada: ");
        int numero = int.Parse(Console.ReadLine());
        for (int i = 1; i <= 10; i++)
        {
            Console.WriteLine($"{numero} x {i} = {numero * i}");
        }
    }

    static void Exercicio12()
    {
        Random rnd = new Random();
        int segredo = rnd.Next(1, 101);
        int palpite = 0;
        while (palpite != segredo)
        {
            Console.Write("Digite seu palpite (1 a 100): ");
            palpite = int.Parse(Console.ReadLine());
            if (palpite < segredo) Console.WriteLine("Mais alto!");
            else if (palpite > segredo) Console.WriteLine("Mais baixo!");
        }
        Console.WriteLine("Parabéns! Você acertou!");
    }
}
