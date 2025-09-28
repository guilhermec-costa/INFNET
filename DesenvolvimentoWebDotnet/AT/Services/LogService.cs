using System.Text;

namespace AgenciaTurismo.Services;

public class LogService
{
    public static List<string> LogMessages { get; } = new List<string>();

    public void LogToConsole(string message)
    {
        Console.WriteLine($"[CONSOLE LOG]: {message}");
    }

    public void LogToFile(string message)
    {
        string logPath = Path.Combine(Directory.GetCurrentDirectory(), "system_log.txt");
        File.AppendAllText(logPath, $"[FILE LOG] {DateTime.Now}: {message}{Environment.NewLine}");
    }

    public void LogToMemory(string message)
    {
        LogMessages.Add($"[MEMORY LOG] {DateTime.Now}: {message}");
    }
}