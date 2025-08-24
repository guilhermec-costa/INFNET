using System;

namespace TP1
{
    class Exercicio3
    {
        public static void Execute()
        {
            Console.WriteLine("=== Sistema de Cálculo de Área de Retângulo ===");
            
            try
            {
                Console.Write("Informe a base do retângulo (metros): ");
                string baseInput = Console.ReadLine();
                
                Console.Write("Informe a altura do retângulo (metros): ");
                string heightInput = Console.ReadLine();
                
                if (double.TryParse(baseInput, out double baseValue) && 
                    double.TryParse(heightInput, out double heightValue))
                {
                    if (baseValue <= 0 || heightValue <= 0)
                    {
                        Console.WriteLine("Erro: Base e altura devem ser valores positivos!");
                        return;
                    }
                    
                    Func<double, double, double> calculateArea = CalculateRectangleArea;
                    
                    double area = calculateArea(baseValue, heightValue);
                    
                    Console.WriteLine("\n" + new string('=', 40));
                    Console.WriteLine("RESULTADO DO CÁLCULO:");
                    Console.WriteLine(new string('=', 40));
                    Console.WriteLine($"Base: {baseValue:F2} metros");
                    Console.WriteLine($"Altura: {heightValue:F2} metros");
                    Console.WriteLine($"Área: {area:F2} metros²");
                    Console.WriteLine(new string('=', 40));
                    
                    Console.WriteLine("\nInformações adicionais:");
                    Console.WriteLine($"Perímetro: {CalculatePerimeter(baseValue, heightValue):F2} metros");
                    Console.WriteLine($"Diagonal: {CalculateDiagonal(baseValue, heightValue):F2} metros");
                }
                else
                {
                    Console.WriteLine("Erro: Por favor, insira valores numéricos válidos!");
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erro: {ex.Message}");
            }
            
            Console.WriteLine("\nPressione qualquer tecla para sair...");
            Console.ReadKey();
        }
        
        static double CalculateRectangleArea(double baseValue, double height)
        {
            return baseValue * height;
        }
        
        static double CalculatePerimeter(double baseValue, double height)
        {
            return 2 * (baseValue + height);
        }
        
        static double CalculateDiagonal(double baseValue, double height)
        {
            return Math.Sqrt(Math.Pow(baseValue, 2) + Math.Pow(height, 2));
        }
    }
}