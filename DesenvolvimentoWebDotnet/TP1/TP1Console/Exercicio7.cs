namespace TP1
{
    public class RobustLogManager
    {
        private Action<string> _loggers;
        
        public RobustLogManager()
        {
            _loggers = null;
        }
        
        public void AddLogger(Action<string> logger)
        {
            _loggers += logger;
        }
        
        public void RemoveLogger(Action<string> logger)
        {
            _loggers -= logger;
        }
        
        public void ClearLoggers()
        {
            _loggers = null;
        }
        
        public void LogSimple(string message)
        {
            Console.WriteLine($"Executando log simples: {message}");
            _loggers?.Invoke(message);
            Console.WriteLine("Log simples concluído");
        }
        
        public void LogRobust(string message)
        {
            Console.WriteLine($"Executando log robusto: {message}");
            
            if (_loggers == null)
            {
                Console.WriteLine("Nenhum logger configurado");
                return;
            }
            
            var invocationList = _loggers.GetInvocationList();
            int successCount = 0;
            int failureCount = 0;
            
            foreach (Action<string> logger in invocationList)
            {
                try
                {
                    logger?.Invoke(message);
                    successCount++;
                }
                catch (Exception ex)
                {
                    failureCount++;
                    Console.WriteLine($"Falha em logger: {ex.Message}");
                }
            }
            
            Console.WriteLine($"Resultado: {successCount} sucessos, {failureCount} falhas");
        }
        
        public int GetLoggerCount()
        {
            return _loggers?.GetInvocationList().Length ?? 0;
        }
    }
    
    class Exercicio7
    {
        public static void Execute()
        {
            Console.WriteLine("=== Exercício 7 ===");
            Console.WriteLine("=== Sistema de Log Robusto com Null-Conditional Operator ===");
            Console.WriteLine();
            
            try
            {
                Logger logger = new Logger();
                RobustLogManager logManager = new RobustLogManager();
                
                Console.WriteLine("=== TESTE 1: Invocação com delegate nulo ===");
                Console.WriteLine("Testando invocação sem nenhum método associado...");
                logManager.LogSimple("Teste com delegate nulo");
                logManager.LogRobust("Teste robusto com delegate nulo");
                
                Console.WriteLine($"Loggers ativos: {logManager.GetLoggerCount()}");
                Console.WriteLine();
                
                Console.WriteLine("=== TESTE 2: Adicionando loggers ===");
                logManager.AddLogger(logger.LogToConsole);
                Console.WriteLine($"Adicionado LogToConsole - Total: {logManager.GetLoggerCount()}");
                
                logManager.AddLogger(logger.LogToFile);
                Console.WriteLine($"Adicionado LogToFile - Total: {logManager.GetLoggerCount()}");
                
                logManager.AddLogger(logger.LogToDatabase);
                Console.WriteLine($"Adicionado LogToDatabase - Total: {logManager.GetLoggerCount()}");
                
                Console.WriteLine();
                Console.WriteLine("=== TESTE 3: Invocação normal ===");
                logManager.LogSimple("Mensagem de teste normal");
                Console.WriteLine();
                
                Console.WriteLine();
                Console.WriteLine("=== TESTE 5: Log robusto com tratamento de exceções ===");
                for (int i = 1; i <= 3; i++)
                {
                    Console.WriteLine($"\n--- Tentativa {i} ---");
                    logManager.LogRobust($"Mensagem robusta #{i}");
                }
                
                Console.WriteLine();
                Console.WriteLine("=== TESTE 7: Limpando todos os loggers ===");
                logManager.ClearLoggers();
                Console.WriteLine($"Loggers limpos - Total: {logManager.GetLoggerCount()}");
                
                Console.WriteLine("Testando invocação após limpeza:");
                logManager.LogSimple("Teste com loggers limpos");
                logManager.LogRobust("Teste robusto com loggers limpos");
                
                Console.WriteLine();
                Console.WriteLine("=== TESTE 8: Demonstração interativa ===");
                Console.WriteLine("Reconfigurando loggers para teste interativo...");
                
                logManager.AddLogger(logger.LogToConsole);
                logManager.AddLogger(logger.LogToFile);
                
                Console.WriteLine("Digite mensagens para log (ou 'sair' para finalizar):");
                string userInput;
                
                while ((userInput = Console.ReadLine()?.Trim()) != "sair" && !string.IsNullOrEmpty(userInput))
                {
                    if (!string.IsNullOrWhiteSpace(userInput))
                    {
                        Console.WriteLine();
                        logManager.LogRobust(userInput);
                        Console.WriteLine();
                    }
                }
                
                Console.WriteLine("\nTodos os testes de robustez concluídos com sucesso!");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erro geral: {ex.Message}");
            }
            Console.WriteLine("\nPressione qualquer tecla para sair...");
            Console.ReadKey();
        }
    }
}