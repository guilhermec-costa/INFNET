using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace AT_BackendDotnet 
{
    public class Aluno
    {
        public string Nome { get; set; }
        public string Matricula { get; set; }
        public string Curso { get; set; }
        public double MediaNotas { get; set; }

        public void ExibirDados()
        {
            Console.WriteLine($"Nome: {Nome}");
            Console.WriteLine($"Matrícula: {Matricula}");
            Console.WriteLine($"Curso: {Curso}");
            Console.WriteLine($"Média: {MediaNotas:F2}");
        }

        public string VerificarAprovacao()
        {
            return MediaNotas >= 7 ? "Aprovado" : "Reprovado";
        }
    }

    public class ContaBancaria
    {
        public string Titular { get; set; }
        private decimal saldo;

        public ContaBancaria(string titular)
        {
            Titular = titular;
            saldo = 0;
        }

        public void Depositar(decimal valor)
        {
            if (valor <= 0)
            {
                Console.WriteLine("O valor do depósito deve ser positivo!");
                return;
            }
            saldo += valor;
            Console.WriteLine($"Depósito de R$ {valor:F2} realizado com sucesso!");
        }

        public void Sacar(decimal valor)
        {
            if (valor > saldo)
            {
                Console.WriteLine("Saldo insuficiente para realizar o saque!");
                return;
            }
            saldo -= valor;
            Console.WriteLine($"Saque de R$ {valor:F2} realizado com sucesso!");
        }

        public void ExibirSaldo()
        {
            Console.WriteLine($"Saldo atual: R$ {saldo:F2}");
        }
    }

    public class Funcionario
    {
        public string Nome { get; set; }
        public string Cargo { get; set; }
        public decimal SalarioBase { get; set; }

        public virtual decimal CalcularSalario()
        {
            return SalarioBase;
        }

        public virtual void ExibirDados()
        {
            Console.WriteLine($"Nome: {Nome}");
            Console.WriteLine($"Cargo: {Cargo}");
            Console.WriteLine($"Salário: R$ {CalcularSalario():F2}");
        }
    }

    public class Gerente : Funcionario
    {
        public override decimal CalcularSalario()
        {
            return SalarioBase * 1.2m; 
        }

        public override void ExibirDados()
        {
            Console.WriteLine($"Nome: {Nome}");
            Console.WriteLine($"Cargo: {Cargo}");
            Console.WriteLine($"Salário (com bônus): R$ {CalcularSalario():F2}");
        }
    }

    public class Produto
    {
        public string Nome { get; set; }
        public int Quantidade { get; set; }
        public decimal Preco { get; set; }

        public override string ToString()
        {
            return $"Produto: {Nome} | Quantidade: {Quantidade} | Preço: R$ {Preco:F2}";
        }
    }

    public class Contato
    {
        public string Nome { get; set; }
        public string Telefone { get; set; }
        public string Email { get; set; }

        public override string ToString()
        {
            return $"{Nome},{Telefone},{Email}";
        }
    }

    public abstract class ContatoFormatter
    {
        public abstract void ExibirContatos(List<Contato> contatos);
    }

    public class MarkdownFormatter : ContatoFormatter
    {
        public override void ExibirContatos(List<Contato> contatos)
        {
            Console.WriteLine("## Lista de Contatos\n");
            foreach (var contato in contatos)
            {
                Console.WriteLine($"- **Nome:** {contato.Nome}");
                Console.WriteLine($"- 📞 Telefone: {contato.Telefone}");
                Console.WriteLine($"- 📧 Email: {contato.Email}\n");
            }
        }
    }

    public class TabelaFormatter : ContatoFormatter
    {
        public override void ExibirContatos(List<Contato> contatos)
        {
            Console.WriteLine("----------------------------------------");
            Console.WriteLine("| Nome | Telefone | Email |");
            Console.WriteLine("----------------------------------------");
            foreach (var contato in contatos)
            {
                Console.WriteLine($"| {contato.Nome} | {contato.Telefone} | {contato.Email} |");
            }
            Console.WriteLine("----------------------------------------");
        }
    }

    public class RawTextFormatter : ContatoFormatter
    {
        public override void ExibirContatos(List<Contato> contatos)
        {
            foreach (var contato in contatos)
            {
                Console.WriteLine($"Nome: {contato.Nome} | Telefone: {contato.Telefone} | Email: {contato.Email}");
            }
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("=== EXECUTANDO TODOS OS EXERCÍCIOS ===\n");

            Exercicio1();
            Console.WriteLine("\n" + new string('=', 50) + "\n");

            Exercicio2();
            Console.WriteLine("\n" + new string('=', 50) + "\n");

            Exercicio3();
            Console.WriteLine("\n" + new string('=', 50) + "\n");

            Exercicio4();
            Console.WriteLine("\n" + new string('=', 50) + "\n");

            Exercicio5();
            Console.WriteLine("\n" + new string('=', 50) + "\n");

            Exercicio6();
            Console.WriteLine("\n" + new string('=', 50) + "\n");

            Exercicio7();
            Console.WriteLine("\n" + new string('=', 50) + "\n");

            Exercicio8();
            Console.WriteLine("\n" + new string('=', 50) + "\n");

            Exercicio9();
            Console.WriteLine("\n" + new string('=', 50) + "\n");

            Exercicio10();
            Console.WriteLine("\n" + new string('=', 50) + "\n");

            Exercicio11_Demo();
            Console.WriteLine("\n" + new string('=', 50) + "\n");

            Exercicio12_Demo();
        }

        static void Exercicio1()
        {
            Console.WriteLine("EXERCÍCIO 1 - Primeiro Programa");
            Console.WriteLine("Olá, meu nome é João Silva!");
            Console.WriteLine("Nasci em 15/03/1990 e estou aprendendo C#!");
        }

        static void Exercicio2()
        {
            Console.WriteLine("EXERCÍCIO 2 - Cifrador de Nome");
            string nome = "Carlos Silva";
            Console.WriteLine($"Entrada: {nome}");
            
            char[] resultado = new char[nome.Length];
            
            for (int i = 0; i < nome.Length; i++)
            {
                char c = nome[i];
                if (char.IsLetter(c))
                {
                    bool ehMinuscula = char.IsLower(c);
                    c = char.ToUpper(c);
                    
                    c = (char)((c - 'A' + 2) % 26 + 'A');
                    
                    if (ehMinuscula)
                        c = char.ToLower(c);
                }
                resultado[i] = c;
            }
            
            string saida = new string(resultado);
            Console.WriteLine($"Saída: {saida}");
            Console.WriteLine("Resultado esperado: Ectnqu Ukngxc");
        }

        static void Exercicio3()
        {
            Console.WriteLine("EXERCÍCIO 3 - Calculadora (Demonstração automática)");
            
            double num1 = 10;
            double num2 = 5;
            int operacao = 1; 
            
            Console.WriteLine($"Número 1: {num1}");
            Console.WriteLine($"Número 2: {num2}");
            Console.WriteLine("Operação escolhida: 1 (Soma)");
            
            double resultado = 0;
            string operador = "";
            
            switch (operacao)
            {
                case 1:
                    resultado = num1 + num2;
                    operador = "+";
                    break;
                case 2:
                    resultado = num1 - num2;
                    operador = "-";
                    break;
                case 3:
                    resultado = num1 * num2;
                    operador = "*";
                    break;
                case 4:
                    if (num2 != 0)
                    {
                        resultado = num1 / num2;
                        operador = "/";
                    }
                    else
                    {
                        Console.WriteLine("Erro: Divisão por zero!");
                        return;
                    }
                    break;
                default:
                    Console.WriteLine("Operação inválida!");
                    return;
            }
            
            Console.WriteLine($"Resultado: {num1} {operador} {num2} = {resultado}");
        }

        static void Exercicio4()
        {
            Console.WriteLine("EXERCÍCIO 4 - Dias até o Próximo Aniversário");
            
            DateTime nascimento = new DateTime(1990, 3, 15);
            DateTime hoje = DateTime.Now;
            DateTime proximoAniversario = new DateTime(hoje.Year, nascimento.Month, nascimento.Day);
            
            if (proximoAniversario < hoje)
            {
                proximoAniversario = proximoAniversario.AddYears(1);
            }
            
            int diasRestantes = (proximoAniversario - hoje).Days;
            
            Console.WriteLine($"Data de nascimento: {nascimento:dd/MM/yyyy}");
            Console.WriteLine($"Data atual: {hoje:dd/MM/yyyy}");
            Console.WriteLine($"Próximo aniversário: {proximoAniversario:dd/MM/yyyy}");
            Console.WriteLine($"Dias restantes: {diasRestantes}");
            
            if (diasRestantes < 7)
            {
                Console.WriteLine("🎉 Seu aniversário está chegando!");
            }
        }

        static void Exercicio5()
        {
            Console.WriteLine("EXERCÍCIO 5 - Tempo Restante para Conclusão do Curso");
            
            DateTime dataAtual = DateTime.Now;
            DateTime dataFormatura = new DateTime(2026, 12, 15);
            
            Console.WriteLine($"Data atual: {dataAtual:dd/MM/yyyy}");
            Console.WriteLine($"Data de formatura: {dataFormatura:dd/MM/yyyy}");
            
            if (dataFormatura < dataAtual)
            {
                Console.WriteLine("Parabéns! Você já deveria estar formado!");
                return;
            }
            
            TimeSpan diferenca = dataFormatura - dataAtual;
            int anos = diferenca.Days / 365;
            int meses = (diferenca.Days % 365) / 30;
            int dias = (diferenca.Days % 365) % 30;
            
            Console.WriteLine($"Faltam {anos} anos, {meses} meses e {dias} dias para sua formatura!");
            
            if (diferenca.TotalDays < 180)
            {
                Console.WriteLine("A reta final chegou! Prepare-se para a formatura!");
            }
        }

        static void Exercicio6()
        {
            Console.WriteLine("EXERCÍCIO 6 - Cadastro de Alunos");
            
            Aluno aluno = new Aluno
            {
                Nome = "João Silva",
                Matricula = "2024001",
                Curso = "Análise e Desenvolvimento de Sistemas",
                MediaNotas = 8.5
            };
            
            aluno.ExibirDados();
            Console.WriteLine($"Status: {aluno.VerificarAprovacao()}");
        }

        static void Exercicio7()
        {
            Console.WriteLine("EXERCÍCIO 7 - Banco Digital (Encapsulamento)");
            
            ContaBancaria conta = new ContaBancaria("João Silva");
            Console.WriteLine($"Titular: {conta.Titular}");
            
            conta.Depositar(500);
            conta.ExibirSaldo();
            
            Console.WriteLine("Tentativa de saque: R$ 700,00");
            conta.Sacar(700);
            
            conta.Sacar(200);
            conta.ExibirSaldo();
        }

        static void Exercicio8()
        {
            Console.WriteLine("EXERCÍCIO 8 - Cadastro de Funcionários (Herança)");
            
            Funcionario funcionario = new Funcionario
            {
                Nome = "Maria Santos",
                Cargo = "Analista",
                SalarioBase = 5000
            };
            
            Gerente gerente = new Gerente
            {
                Nome = "Carlos Lima",
                Cargo = "Gerente",
                SalarioBase = 8000
            };
            
            Console.WriteLine("--- Funcionário ---");
            funcionario.ExibirDados();
            
            Console.WriteLine("\n--- Gerente ---");
            gerente.ExibirDados();
        }

        static void Exercicio9()
        {
            Console.WriteLine("EXERCÍCIO 9 - Controle de Estoque (Demonstração)");
            
            Produto[] produtos = new Produto[5];
            int contador = 0;
            
            produtos[contador++] = new Produto { Nome = "Notebook", Quantidade = 2, Preco = 4500.00m };
            produtos[contador++] = new Produto { Nome = "Mouse", Quantidade = 10, Preco = 75.50m };
            produtos[contador++] = new Produto { Nome = "Teclado", Quantidade = 5, Preco = 150.00m };
            
            Console.WriteLine("=== PARTE A: Array de Produtos ===");
            Console.WriteLine("Produtos cadastrados no array:");
            for (int i = 0; i < contador; i++)
            {
                Console.WriteLine(produtos[i].ToString());
            }
            
            if (contador >= 5)
            {
                Console.WriteLine("Limite de produtos atingido!");
            }
            
            Console.WriteLine("\n=== PARTE B: Persistência com Arquivos ===");
            
            try
            {
                using (StreamWriter writer = new StreamWriter("estoque.txt"))
                {
                    for (int i = 0; i < contador; i++)
                    {
                        writer.WriteLine($"{produtos[i].Nome},{produtos[i].Quantidade},{produtos[i].Preco:F2}");
                    }
                }
                Console.WriteLine("Produtos salvos no arquivo 'estoque.txt'");
                
                Console.WriteLine("\nLendo produtos do arquivo:");
                if (File.Exists("estoque.txt"))
                {
                    string[] linhas = File.ReadAllLines("estoque.txt");
                    if (linhas.Length == 0)
                    {
                        Console.WriteLine("Nenhum produto cadastrado.");
                    }
                    else
                    {
                        foreach (string linha in linhas)
                        {
                            string[] dados = linha.Split(',');
                            if (dados.Length == 3)
                            {
                                Console.WriteLine($"Produto: {dados[0]} | Quantidade: {dados[1]} | Preço: R$ {dados[2]}");
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erro ao manipular arquivo: {ex.Message}");
            }
        }

        static void Exercicio10()
        {
            Console.WriteLine("EXERCÍCIO 10 - Jogo de Adivinhação (Demonstração)");
            
            Random random = new Random();
            int numeroSecreto = random.Next(1, 51);
            int tentativas = 5;
            
            Console.WriteLine("Jogo: Adivinhe o número de 1 a 50!");
            Console.WriteLine($"Você tem {tentativas} tentativas.");
            
            int[] palpites = { 25, 35, 42, numeroSecreto }; 
            
            for (int i = 0; i < palpites.Length && tentativas > 0; i++)
            {
                int palpite = palpites[i];
                Console.WriteLine($"\nTentativa {i + 1}: {palpite}");
                tentativas--;
                
                if (palpite == numeroSecreto)
                {
                    Console.WriteLine("🎉 Parabéns! Você acertou!");
                    return;
                }
                else if (palpite < numeroSecreto)
                {
                    Console.WriteLine("O número é maior!");
                }
                else
                {
                    Console.WriteLine("O número é menor!");
                }
                
                Console.WriteLine($"Tentativas restantes: {tentativas}");
            }
            
            if (tentativas == 0)
            {
                Console.WriteLine($"\nGame Over! O número era {numeroSecreto}");
            }
        }

        static void Exercicio11_Demo()
        {
            Console.WriteLine("EXERCÍCIO 11 - Gerenciador de Contatos (Demonstração)");
            
            List<Contato> contatos = new List<Contato>
            {
                new Contato { Nome = "João Silva", Telefone = "(21) 99999-9999", Email = "joao@email.com" },
                new Contato { Nome = "Maria Oliveira", Telefone = "(11) 98888-7777", Email = "maria@email.com" }
            };
            
            try
            {
                using (StreamWriter writer = new StreamWriter("contatos.txt"))
                {
                    foreach (var contato in contatos)
                    {
                        writer.WriteLine(contato.ToString());
                    }
                }
                Console.WriteLine("Contatos salvos no arquivo 'contatos.txt'");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erro ao salvar contatos: {ex.Message}");
            }
            
            Console.WriteLine("\nContatos cadastrados:");
            foreach (var contato in contatos)
            {
                Console.WriteLine($"Nome: {contato.Nome} | Telefone: {contato.Telefone} | Email: {contato.Email}");
            }
        }

        static void Exercicio12_Demo()
        {
            Console.WriteLine("EXERCÍCIO 12 - Formatos de Exibição (Herança e Polimorfismo)");
            
            List<Contato> contatos = new List<Contato>();
            
            try
            {
                if (File.Exists("contatos.txt"))
                {
                    string[] linhas = File.ReadAllLines("contatos.txt");
                    foreach (string linha in linhas)
                    {
                        string[] dados = linha.Split(',');
                        if (dados.Length == 3)
                        {
                            contatos.Add(new Contato 
                            { 
                                Nome = dados[0], 
                                Telefone = dados[1], 
                                Email = dados[2] 
                            });
                        }
                    }
                }
                
                if (contatos.Count == 0)
                {
                    contatos.Add(new Contato { Nome = "João Silva", Telefone = "(21) 99999-9999", Email = "joao@email.com" });
                    contatos.Add(new Contato { Nome = "Maria Oliveira", Telefone = "(11) 98888-7777", Email = "maria@email.com" });
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erro ao ler arquivo de contatos: {ex.Message}");
                contatos.Add(new Contato { Nome = "João Silva", Telefone = "(21) 99999-9999", Email = "joao@email.com" });
                contatos.Add(new Contato { Nome = "Maria Oliveira", Telefone = "(11) 98888-7777", Email = "maria@email.com" });
            }
            
            Console.WriteLine("\n--- Formato Markdown ---");
            ContatoFormatter formatter = new MarkdownFormatter();
            formatter.ExibirContatos(contatos);
            
            Console.WriteLine("\n--- Formato Tabela ---");
            formatter = new TabelaFormatter();
            formatter.ExibirContatos(contatos);
            
            Console.WriteLine("\n--- Formato Texto Puro ---");
            formatter = new RawTextFormatter();
            formatter.ExibirContatos(contatos);
        }
    }
}