namespace TP1 
{
    public class DownloadCompletedEventArgs : EventArgs
    {
        public string FileName { get; set; }
        public long FileSize { get; set; }
        public TimeSpan Duration { get; set; }
        public DateTime CompletedAt { get; set; }
        public bool Success { get; set; }
        
        public DownloadCompletedEventArgs(string fileName, long fileSize, TimeSpan duration, bool success)
        {
            FileName = fileName;
            FileSize = fileSize;
            Duration = duration;
            Success = success;
            CompletedAt = DateTime.Now;
        }
    }
    
    public class DownloadManager
    {
        public event EventHandler<DownloadCompletedEventArgs> DownloadCompleted;
        
        public void StartDownload(string fileName, long fileSize, int downloadTimeSeconds)
        {
            Console.WriteLine($"Iniciando download: {fileName}");
            Console.WriteLine($"Tamanho do arquivo: {FormatFileSize(fileSize)}");
            Console.WriteLine($"️Tempo estimado: {downloadTimeSeconds} segundos");
            Console.WriteLine();
            
            DateTime startTime = DateTime.Now;
            
            try
            {
                for (int i = 1; i <= downloadTimeSeconds; i++)
                {
                    Thread.Sleep(1000);
                    int progress = (int)((double)i / downloadTimeSeconds * 100);
                    ShowProgress(progress, i, downloadTimeSeconds);
                }
                
                TimeSpan duration = DateTime.Now - startTime;
                OnDownloadCompleted(fileName, fileSize, duration, true);
            }
            catch (Exception ex)
            {
                TimeSpan duration = DateTime.Now - startTime;
                Console.WriteLine($"\nErro durante o download: {ex.Message}");
                OnDownloadCompleted(fileName, fileSize, duration, false);
            }
        }
        
        protected virtual void OnDownloadCompleted(string fileName, long fileSize, TimeSpan duration, bool success)
        {
            DownloadCompleted?.Invoke(this, new DownloadCompletedEventArgs(fileName, fileSize, duration, success));
        }
        
        private void ShowProgress(int percentage, int currentSecond, int totalSeconds)
        {
            string progressBar = new string('█', percentage / 5) + new string('░', 20 - (percentage / 5));
            Console.Write($"\r[{progressBar}] {percentage}% ({currentSecond}/{totalSeconds}s)");
            
            if (percentage == 100)
                Console.WriteLine();
        }
        
        private string FormatFileSize(long bytes)
        {
            string[] sizes = { "B", "KB", "MB", "GB" };
            double size = bytes;
            int order = 0;
            
            while (size >= 1024 && order < sizes.Length - 1)
            {
                order++;
                size = size / 1024;
            }
            
            return $"{size:F2} {sizes[order]}";
        }
    }
    
    class Exercicio5
    {
        public static void Execute()
        {
            Console.WriteLine("=== Exercício 5 ===");
            Console.WriteLine("=== Sistema de Gerenciamento de Downloads ===");
            Console.WriteLine();
            
            try
            {
                DownloadManager downloadManager = new DownloadManager();
                downloadManager.DownloadCompleted += OnDownloadCompleted;
                downloadManager.DownloadCompleted += OnDownloadStatistics;
                
                Console.WriteLine("Iniciando simulação de downloads...");
                Console.WriteLine();
                
                downloadManager.StartDownload("documento.pdf", 2048576, 3);
                Console.WriteLine();
                
                downloadManager.StartDownload("video.mp4", 104857600, 5);
                Console.WriteLine();
                
                downloadManager.StartDownload("software.exe", 524288000, 4);
                Console.WriteLine();
                
                Console.WriteLine("Todos os downloads foram processados!");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erro: {ex.Message}");
            }
            
            Console.WriteLine("\nPressione qualquer tecla para sair...");
            Console.ReadKey();
        }
        
        static void OnDownloadCompleted(object sender, DownloadCompletedEventArgs e)
        {
            Console.WriteLine();
            
            if (e.Success)
            {
                Console.WriteLine("DOWNLOAD CONCLUÍDO COM SUCESSO!");
                Console.WriteLine($"Arquivo: {e.FileName}");
                Console.WriteLine($"Tamanho: {FormatFileSize(e.FileSize)}");
                Console.WriteLine($"Duração: {e.Duration.TotalSeconds:F1} segundos");
                Console.WriteLine($"Concluído em: {e.CompletedAt:dd/MM/yyyy HH:mm:ss}");
                Console.WriteLine($"Velocidade média: {CalculateSpeed(e.FileSize, e.Duration)} MB/s");
            }
            else
            {
                Console.WriteLine("DOWNLOAD FALHOU!");
                Console.WriteLine($"Arquivo: {e.FileName}");
                Console.WriteLine($"️Tempo decorrido: {e.Duration.TotalSeconds:F1} segundos");
                Console.WriteLine($"Falha em: {e.CompletedAt:dd/MM/yyyy HH:mm:ss}");
            }
        }
        
        static void OnDownloadStatistics(object sender, DownloadCompletedEventArgs e)
        {
            if (e.Success)
            {
                Console.WriteLine($"Estatísticas registradas para {e.FileName}");
            }
        }
        
        static string FormatFileSize(long bytes)
        {
            string[] sizes = { "B", "KB", "MB", "GB" };
            double size = bytes;
            int order = 0;
            
            while (size >= 1024 && order < sizes.Length - 1)
            {
                order++;
                size = size / 1024;
            }
            
            return $"{size:F2} {sizes[order]}";
        }
        
        static double CalculateSpeed(long bytes, TimeSpan duration)
        {
            double megabytes = bytes / (1024.0 * 1024.0);
            return megabytes / duration.TotalSeconds;
        }
    }
}