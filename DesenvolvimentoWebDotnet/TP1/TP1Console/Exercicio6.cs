namespace TP1 
{
    public class Logger
    {
        private readonly string _logFilePath;
        
        public Logger()
        {
            _logFilePath = Path.Combine(Environment.CurrentDirectory, "application.log");
            
            if (!File.Exists(_logFilePath))
            {
                File.Create(_logFilePath).Close();
            }
        }
        
        public void LogToConsole(string message)
        {
            string timestamp = DateTime.Now.ToString("dd/MM/yyyy HH:mm:ss");
            string formattedMessage = $"[CONSOLE] {timestamp} - {message}";
            
            Console.WriteLine("📺 " + formattedMessage);
        }
        
        public void LogToFile(string message)
        {
            try
            {
                string timestamp = DateTime.Now.ToString("dd/MM/yyyy HH:mm:ss");
                string formattedMessage = $"[FILE] {timestamp} - {message}";
                
                File.AppendAllText(_logFilePath, formattedMessage + Environment.NewLine);
                Console.WriteLine("💾 Log gravado em arquivo");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"❌ Erro ao gravar no arquivo: {ex.Message}");
            }
        }
        
        public void LogToDatabase(string message)
        {
            try
            {
                string timestamp = DateTime.Now.ToString("dd/MM/yyyy HH:mm:ss");
                
                Thread.Sleep(100);
                Console.WriteLine($"🗄️  Log gravado no banco: ID_{DateTime.Now.Ticks} - {message}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"❌ Erro ao gravar no banco: {ex.Message}");
            }
        }
        
        public void ShowLogStatistics()
        {
            try
            {
                if (File.Exists(_logFilePath))
                {
                    string[] lines = File.ReadAllLines(_logFilePath);
                    Console.WriteLine($"📊 Total de logs em arquivo: {lines.Length}");
                }
                else
                {
                    Console.WriteLine("📊 Nenhum log encontrado em arquivo");
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"❌ Erro ao ler estatísticas: {ex.Message}");
            }
        }
    }
    
    class Exercicio6
    {
        public static void Execute()
        {
            Console.WriteLine("=== Sistema de Registro com Multicast Delegate ===");
            Console.WriteLine();
            
            try
            {
                Logger logger = new Logger();
                Action<string> multicastLogger = null;
                
                multicastLogger += logger.LogToConsole;
                multicastLogger += logger.LogToFile;
                multicastLogger += logger.LogToDatabase;
                
                Console.WriteLine("Sistema de log multicast configurado!");
                Console.WriteLine("Destinos: Console + Arquivo + Banco de Dados");
                Console.WriteLine();
                
                Console.WriteLine("=== TESTE 1: Log de início da aplicação ===");
                multicastLogger?.Invoke("Aplicação iniciada com sucesso");
                
                Console.WriteLine();
                Console.WriteLine("=== TESTE 2: Log de operação de usuário ===");
                multicastLogger?.Invoke("Usuário realizou login - ID: user123");
                
                Console.WriteLine();
                Console.WriteLine("=== TESTE 3: Log de erro ===");
                multicastLogger?.Invoke("ERRO: Falha na conexão com serviço externo");
                
                Console.WriteLine();
                Console.WriteLine("=== TESTE 4: Log de processo de negócio ===");
                multicastLogger?.Invoke("Pedido #12345 processado - Status: Aprovado");
                
                Console.WriteLine();
                Console.WriteLine("=== TESTE 5: Demonstração interativa ===");
                Console.WriteLine("Digite suas próprias mensagens de log (ou 'sair' para finalizar):");
                
                string userInput;
                int logCount = 0;
                
                while ((userInput = Console.ReadLine()?.Trim()) != "sair" && !string.IsNullOrEmpty(userInput))
                {
                    if (!string.IsNullOrWhiteSpace(userInput))
                    {
                        logCount++;
                        Console.WriteLine($"\n--- Log #{logCount} ---");
                        multicastLogger?.Invoke(userInput);
                        Console.WriteLine();
                    }
                }
                
                Console.WriteLine();
                Console.WriteLine("=== ESTATÍSTICAS FINAIS ===");
                logger.ShowLogStatistics();
                multicastLogger?.Invoke("Sistema de log finalizado");
                
                Console.WriteLine();
                Console.WriteLine("=== TESTE DE REMOÇÃO DE DESTINO ===");
                multicastLogger -= logger.LogToDatabase;
                Console.WriteLine("Removido: LogToDatabase");
                Console.WriteLine("Testando log apenas para Console + Arquivo:");
                multicastLogger?.Invoke("Teste sem banco de dados");
                
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erro: {ex.Message}");
            }
            
            Console.WriteLine("\nPressione qualquer tecla para sair...");
            Console.ReadKey();
        }
    }
}