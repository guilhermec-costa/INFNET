namespace TP1
{
    public delegate decimal CalculateDiscount(decimal originalPrice);
    
    class Exercicio1
    {
        public static void Execute()
        {
            Console.WriteLine("=== Sistema de Cálculo de Desconto ===");
            
            try
            {
                Console.Write("Informe o preço original do produto (R$): ");
                string input = Console.ReadLine();
                
                if (decimal.TryParse(input, out decimal originalPrice))
                {
                    CalculateDiscount discountCalculator = ApplyTenPercentDiscount;
                    
                    decimal finalPrice = discountCalculator(originalPrice);
                    
                    Console.WriteLine($"\nPreço original: R$ {originalPrice:F2}");
                    Console.WriteLine($"Desconto aplicado: 10%");
                    Console.WriteLine($"Valor do desconto: R$ {(originalPrice - finalPrice):F2}");
                    Console.WriteLine($"Preço final: R$ {finalPrice:F2}");
                }
                else
                {
                    Console.WriteLine("Valor inválido! Por favor, insira um número válido.");
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erro: {ex.Message}");
            }
            
            Console.WriteLine("\nPressione qualquer tecla para sair...");
            Console.ReadKey();
        }
        
        static decimal ApplyTenPercentDiscount(decimal originalPrice)
        {
            const decimal discountPercentage = 0.10m;
            return originalPrice * (1 - discountPercentage);
        }
    }
}