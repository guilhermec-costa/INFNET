namespace TP1
{
    public class TemperatureExceededEventArgs : EventArgs
    {
        public double Temperature { get; set; }
        public DateTime Timestamp { get; set; }
        public string SensorId { get; set; }
        
        public TemperatureExceededEventArgs(double temperature, string sensorId)
        {
            Temperature = temperature;
            SensorId = sensorId;
            Timestamp = DateTime.Now;
        }
    }
    
    public class TemperatureSensor
    {
        private const double SAFE_TEMPERATURE_LIMIT = 100.0;
        private readonly string _sensorId;
        
        public event EventHandler<TemperatureExceededEventArgs> TemperatureExceeded;
        
        public TemperatureSensor(string sensorId)
        {
            _sensorId = sensorId;
        }
        
        public string SensorId => _sensorId;
        
        public void ReadTemperature(double temperature)
        {
            Console.WriteLine($"[{DateTime.Now:HH:mm:ss}] Sensor {_sensorId}: {temperature:F1}°C");
            if (temperature > SAFE_TEMPERATURE_LIMIT)
            {
                OnTemperatureExceeded(temperature);
            }
        }
        
        protected virtual void OnTemperatureExceeded(double temperature)
        {
            TemperatureExceeded?.Invoke(this, new TemperatureExceededEventArgs(temperature, _sensorId));
        }
    }
    
    class Exercicio4
    {
        public static void Execute()
        {
            Console.WriteLine("=== Exercício 4 ===");
            Console.WriteLine("=== Sistema de Monitoramento de Temperatura ===");
            Console.WriteLine("Limite seguro de temperatura: 100°C");
            Console.WriteLine();
            
            try
            {
                TemperatureSensor sensor = new TemperatureSensor("TEMP-001");
                sensor.TemperatureExceeded += OnTemperatureAlert;
                sensor.TemperatureExceeded += LogTemperatureExceeded;
                
                Console.WriteLine("Sistema de monitoramento iniciado.");
                Console.WriteLine("Digite temperaturas para simular leituras (ou 'sair' para finalizar):");
                Console.WriteLine();
                
                string input;
                while ((input = Console.ReadLine()?.ToLower()) != "sair")
                {
                    if (double.TryParse(input, out double temperature))
                    {
                        sensor.ReadTemperature(temperature);
                    }
                    else if (!string.IsNullOrWhiteSpace(input))
                    {
                        Console.WriteLine("❌ Valor inválido! Digite um número ou 'sair'.");
                    }
                    
                    Console.WriteLine();
                }
                
                Console.WriteLine("Sistema de monitoramento finalizado.");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erro: {ex.Message}");
            }
            
            Console.WriteLine("\nPressione qualquer tecla para sair...");
            Console.ReadKey();
        }
        
        static void OnTemperatureAlert(object sender, TemperatureExceededEventArgs e)
        {
            Console.WriteLine();
            Console.WriteLine("ALERTA DE TEMPERATURA CRÍTICA!");
            Console.WriteLine($"Sensor: {e.SensorId}");
            Console.WriteLine($"Temperatura: {e.Temperature:F1}°C");
            Console.WriteLine($"Excesso: +{(e.Temperature - 100):F1}°C acima do limite");
            Console.WriteLine($"Timestamp: {e.Timestamp:dd/MM/yyyy HH:mm:ss}");
            Console.WriteLine("Ação necessária: Verificar equipamento imediatamente!");
        }
        
        static void LogTemperatureExceeded(object sender, TemperatureExceededEventArgs e)
        {
            Console.WriteLine($"LOG: Temperatura crítica registrada - {e.SensorId} - {e.Temperature:F1}°C");
        }
    }
}