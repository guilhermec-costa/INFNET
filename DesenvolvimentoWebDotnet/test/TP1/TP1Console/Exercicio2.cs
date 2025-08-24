using System;

namespace TP1 
{
    class Exercicio2
    {
        public static void Execute()
        {
            Console.WriteLine("=== Sistema de Mensagens Multilíngues ===");
            
            try
            {
                Console.WriteLine("Escolha um idioma:");
                Console.WriteLine("1 - Português");
                Console.WriteLine("2 - Inglês");
                Console.WriteLine("3 - Espanhol");
                Console.Write("Digite sua opção (1-3): ");
                
                string choice = Console.ReadLine();
                
                Action<string> welcomeMessage = null;
                
                switch (choice)
                {
                    case "1":
                        welcomeMessage = ShowWelcomePortuguese;
                        break;
                    case "2":
                        welcomeMessage = ShowWelcomeEnglish;
                        break;
                    case "3":
                        welcomeMessage = ShowWelcomeSpanish;
                        break;
                    default:
                        Console.WriteLine("Opção inválida! Usando português como padrão.");
                        welcomeMessage = ShowWelcomePortuguese;
                        break;
                }
                
                Console.Write("Digite seu nome: ");
                string userName = Console.ReadLine();
                
                if (string.IsNullOrWhiteSpace(userName))
                {
                    userName = "Usuário";
                }
                
                Console.WriteLine("\n" + new string('=', 50));
                
                welcomeMessage?.Invoke(userName);
                
                Console.WriteLine(new string('=', 50));
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erro: {ex.Message}");
            }
            
            Console.WriteLine("\nPressione qualquer tecla para sair...");
            Console.ReadKey();
        }
        
        static void ShowWelcomePortuguese(string userName)
        {
            Console.WriteLine($"Bem-vindo(a), {userName}!");
            Console.WriteLine("Obrigado por usar nossa aplicação.");
            Console.WriteLine("Esperamos que tenha uma excelente experiência!");
        }
        
        static void ShowWelcomeEnglish(string userName)
        {
            Console.WriteLine($"Welcome, {userName}!");
            Console.WriteLine("Thank you for using our application.");
            Console.WriteLine("We hope you have an excellent experience!");
        }
        
        static void ShowWelcomeSpanish(string userName)
        {
            Console.WriteLine($"Bienvenido(a), {userName}!");
            Console.WriteLine("Gracias por usar nuestra aplicación.");
            Console.WriteLine("¡Esperamos que tengas una excelente experiencia!");
        }
    }
}